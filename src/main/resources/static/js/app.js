/* NOVA hotel front end: talks to the Spring Boot backend (see API list in README). */
(function(){"use strict";
function h(tag,attrs){
    var el=document.createElement(tag);
    if(attrs)for(var k in attrs){
        if(k==="class")el.className=attrs[k];
        else if(k==="text")el.textContent=attrs[k];
        else if(k.slice(0,2)==="on")el.addEventListener(k.slice(2),attrs[k]);
        else el.setAttribute(k,attrs[k]);
    }
    for(var i=2;i<arguments.length;i++){var c=arguments[i];if(c==null)continue;el.appendChild(typeof c==="string"?document.createTextNode(c):c)}
    return el;
}
var mem={};
function lsGet(k,def){try{var v=localStorage.getItem(k);if(v!==null)return JSON.parse(v)}catch(e){}return (k in mem)?mem[k]:def}
function lsSet(k,v){mem[k]=v;try{localStorage.setItem(k,JSON.stringify(v))}catch(e){}}
var toastEl=document.getElementById("toast"),toastT;
function toast(msg){toastEl.textContent=msg;toastEl.classList.add("show");clearTimeout(toastT);toastT=setTimeout(function(){toastEl.classList.remove("show")},2800)}
window.__novaToast=toast;
window.__nova={h:h,lsGet:lsGet,lsSet:lsSet};

/* active menu link while scrolling */
var ids=["top","about","rooms","gallery","reviews","contact"],links=[].slice.call(document.querySelectorAll(".nav-link[data-spy]"));
function spy(){var y=window.scrollY+160,on="top";ids.forEach(function(id){var el=document.getElementById(id);if(el&&el.offsetTop<=y)on=id});links.forEach(function(a){a.classList.toggle("on",a.getAttribute("data-spy")===on)})}
window.addEventListener("scroll",spy,{passive:true});spy();
links.forEach(function(a){a.addEventListener("click",function(){var n=document.getElementById("mainnav");if(n&&n.classList.contains("show")){var t=document.querySelector(".navbar-toggler");if(t)t.click()}})});
document.getElementById("yr").textContent=new Date().getFullYear();

/* arrival / departure / guests widget: remembers choices, then jumps to the rooms */
var d=new Date();d.setMinutes(d.getMinutes()-d.getTimezoneOffset());var today=d.toISOString().slice(0,10);
var sin=document.getElementById("s-in"),sout=document.getElementById("s-out");sin.min=sout.min=today;
sin.addEventListener("change",function(){if(sin.value){sout.min=sin.value;if(sout.value&&sout.value<=sin.value)sout.value=""}});
document.getElementById("stay").addEventListener("submit",function(e){
    e.preventDefault();var er=document.getElementById("stayerr");er.textContent="";
    if((sin.value&&!sout.value)||(!sin.value&&sout.value)){er.textContent="Choose both arrival and departure dates.";return}
    if(sin.value&&sout.value&&sout.value<=sin.value){er.textContent="Departure must be after arrival.";return}
    window.__stay=sin.value?{in:sin.value,out:sout.value}:null;
    if(window.__setGuests)window.__setGuests(parseInt(document.getElementById("s-g").value,10)||1);
    document.getElementById("rooms").scrollIntoView();
});
/* contact form opens the guest's email app (the backend has no message endpoint) */
document.getElementById("request").addEventListener("submit",function(e){
    e.preventDefault();var n=document.getElementById("e-name").value.trim(),m=document.getElementById("e-mail").value.trim(),p=document.getElementById("e-phone").value.trim(),g=document.getElementById("e-msg").value.trim(),er=document.getElementById("eerr");
    if(!n||!/^\S+@\S+\.\S+$/.test(m)||g.length<3){er.textContent="Please enter your name, a valid email and a short message.";return}
    er.textContent="";
    location.href="mailto:desk@nova-hotel.example?subject="+encodeURIComponent("Enquiry from "+n)+"&body="+encodeURIComponent(g+"\n\n"+n+(p?" / "+p:"")+" / "+m);
});
})();

    (function(){
        "use strict";
        var N=window.__nova,h=N.h,lsGet=N.lsGet,lsSet=N.lsSet,toast=window.__novaToast;

        /* ---------- connection to the Spring Boot backend ---------- */
        // Page served by the backend (port 8080)? use relative addresses. Opened any other way? use localhost:8080.
        var ROOT=(location.protocol.indexOf("http")===0&&location.port==="8080")?"":"http://localhost:8080";
        var API=ROOT+"/api/v1";          // rooms + bookings
        var AUTH=ROOT+"/auth/customer";  // POST  {userName,password}  -> customer or empty (wrong login)
        var PAY=ROOT+"/payment";         // POST /newpay, GET /payments, PUT /{id}, DELETE /{id}
        var REV=ROOT+"/review";          // POST /newreview, GET /reviews, PUT /{id}, DELETE /{id}
        var JSONH={"Content-Type":"application/json"};
        var CUST=ROOT+"/customer";       // POST /register, GET /{id}, PUT /{id}, DELETE /{id}, GET /customers

        var ROOMS=[],LOAD_ERROR="",LOADED=false,FILTER="All",GUESTS=1;
        var BLURB={Standard:"A comfortable room with everything you need for a good stay.",Deluxe:"More space and extra comfort for a relaxed stay.",Suite:"Our most spacious room, made for a special stay."};
        var state={user:null,pop:false,confirmDel:false,editPhone:false,mine:[],reviews:[],pays:[]};
        var SESSION_KEY="staylanka_customer_id";
        function errText(t){t=String(t||"").trim();try{var j=JSON.parse(t);if(j&&typeof j==="object")return String(j.message||j.error||"")}catch(e){}return t.length>200?"":t}
        function nightsBetween(a,b){return Math.max(1,Math.round((new Date(b)-new Date(a))/86400000))}

        /* ---------- backend helpers ---------- */
        async function apiLogin(userName,password){
            var res=await fetch(AUTH,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({userName:userName,password:password})});
            if(!res.ok)throw new Error("Server answered "+res.status);
            var text=await res.text();
            if(!text)return null;                    // backend returns an empty body for a wrong username / password
            try{return JSON.parse(text)}catch(e){return null}
        }
        async function apiRegister(userName,password,phoneNumber){
            var res=await fetch(CUST+"/register",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({userName:userName,password:password,phoneNumber:phoneNumber})});
            if(!res.ok)throw new Error("Server answered "+res.status);
            return res.json();
        }
        async function usernameTaken(userName){
            var res=await fetch(CUST+"/customers");
            if(!res.ok)throw new Error("Server answered "+res.status);
            var list=await res.json();
            return list.some(function(c){return String(c.userName).toLowerCase()===userName.toLowerCase()});
        }
        async function apiGetCustomer(id){
            var res=await fetch(CUST+"/"+id);
            if(!res.ok)return null;
            var text=await res.text();
            if(!text)return null;
            try{return JSON.parse(text)}catch(e){return null}
        }

        /* ---------- rooms (loaded from the backend) ---------- */
        /* each room type gets its own photos; rooms of the same type alternate between them */
        var ROOM_IMAGES={Standard:[1,2],Deluxe:[4,6],Suite:[3,5]};
        function roomImage(r){
            var list=ROOM_IMAGES[r.type]||[1,2,3,4,5,6];
            var n=parseInt(String(r.id).replace(/\D/g,""),10)||0;
            return "images/room"+list[n%list.length]+".jpg";
        }
        async function loadRooms(){
            try{
                var res=await fetch(API+"/rooms");
                if(!res.ok)throw new Error("Server answered "+res.status);
                var list=await res.json();
                LOADED=true;ROOMS=list.map(function(r){return{id:r.roomId,type:r.type,capacity:r.capacity,price:r.pricePerNight,note:BLURB[r.type]||"A comfortable room for your stay."}});
                LOAD_ERROR="";
            }catch(e){LOADED=true;ROOMS=[];LOAD_ERROR="Cannot reach the room server. Start the Spring Boot app and refresh this page."}
            renderRooms();
        }

        var grid=document.getElementById("roomgrid");
        function hasMyBooking(roomId){return state.mine.some(function(b){return b.roomId===roomId&&b.status==="Confirmed"})}
        var fbox=document.getElementById("filters");
        window.__setGuests=function(n){GUESTS=n;renderRooms()};
        function renderFilters(){
            fbox.textContent="";
            var types=["All"];ROOMS.forEach(function(r){if(types.indexOf(r.type)<0)types.push(r.type)});
            if(types.length<3)return;
            types.forEach(function(t){fbox.appendChild(h("button",{class:"chip",type:"button",text:t==="All"?"All rooms":t,"aria-pressed":String(FILTER===t),onclick:function(){FILTER=t;renderRooms()}}))});
        }
        function renderHomeReviews(){
            var box=document.getElementById("revhome"),note=document.getElementById("revnote");
            var list=state.reviews.filter(function(r){return r.comment}).sort(function(a,b){return (revId(b)||0)-(revId(a)||0)}).slice(0,3);
            box.textContent="";
            note.textContent=list.length?"Honest reviews from guests who stayed with us.":"No reviews yet. Guests who have paid and stayed can review a room from the Our Room section.";
            list.forEach(function(r,i){
                box.appendChild(h("div",{class:"col-md-4"},h("div",{class:"blog_box"},
                    h("div",{class:"blog_img"},h("figure",null,h("img",{src:"images/blog"+(i%3+1)+".jpg",alt:""}))),
                    h("div",{class:"blog_room"},h("h3",{text:"Room "+revRoomId(r)}),
                        h("span",{class:"stars",text:stars(r.rating)+"  "+((r.user&&r.user.userName)||"Guest")}),
                        h("p",{text:r.comment})))));
            });
        }
        function renderRooms(){
            grid.textContent="";renderFilters();renderHomeReviews();
            if(LOAD_ERROR){grid.appendChild(h("p",{class:"lead msg",text:LOAD_ERROR}));return}
            if(!LOADED){grid.appendChild(h("p",{class:"lead msg",text:"Loading rooms..."}));return}
            var n=0;
            if(GUESTS>1||(window.__stay&&window.__stay.in)){
                var bits=[];if(GUESTS>1)bits.push("for "+GUESTS+(GUESTS>=4?"+":"")+" guests");
                if(window.__stay&&window.__stay.in&&window.__stay.out)bits.push(window.__stay.in+" to "+window.__stay.out);
                grid.appendChild(h("p",{class:"roomnote"},"Showing rooms "+bits.join(", ")+". ",h("button",{type:"button",text:"Clear",onclick:function(){GUESTS=1;window.__stay=null;renderRooms()}})));
            }
            ROOMS.forEach(function(r){
                if(FILTER!=="All"&&r.type!==FILTER)return;
                if(GUESTS>1&&r.capacity<GUESTS)return;
                n++;
                var mine=hasMyBooking(r.id),btn,img=roomImage(r);
                if(state.user)btn=h("button",{class:"btn solid",type:"button",text:"Book Now",onclick:function(){openModal("book",r)}});
                else btn=h("button",{class:"btn solid",type:"button",text:"Sign in to book",onclick:function(){openModal("login","Sign in to book a room.")}});
                grid.appendChild(h("div",{class:"col-md-4 col-sm-6"},h("div",{class:"room"},
                    h("div",{class:"room_img"},h("figure",null,h("img",{src:img,alt:r.type+" room"})),
                        h("span",{class:"tag",text:"Room "+r.id}),mine?h("span",{class:"tag mine",text:"Your booking"}):null),
                    h("div",{class:"bed_room"},
                        h("h3",{text:r.type+" Room"}),
                        h("p",{text:r.note}),
                        h("div",{class:"rmeta"},h("span",{text:"Sleeps "+r.capacity}),h("button",{type:"button",class:"rvlink",text:revText(r.id),onclick:function(){openModal("reviews",r)}})),
                        h("div",{class:"rfoot"},h("div",{class:"price"},"Rs. "+r.price,h("small",{text:" / night"})),btn)))));
            });
            if(!n)grid.appendChild(h("p",{class:"lead msg",text:"No rooms match your choice. Try a smaller party size or another room type."}));
        }

        /* ---------- my bookings (looked up on the backend by guest name + phone) ---------- */
        async function loadMine(){
            state.mine=[];
            if(!state.user){renderRooms();return}
            try{
                var res=await fetch(API+"/bookings?keyword="+encodeURIComponent(state.user.userName));
                if(res.ok){
                    var list=await res.json(),u=state.user;
                    state.mine=list.filter(function(b){return String(b.guestName||"").toLowerCase()===String(u.userName).toLowerCase()});
                }
            }catch(e){}
            renderRooms();if(state.pop)renderPop();
        }
        async function cancelMine(id){
            if(!confirm(payFor(id)?"Cancel this booking? Its payment record stays on file.":"Cancel this booking?"))return;
            try{var res=await fetch(API+"/bookings/"+id+"/cancel",{method:"PUT"});toast(res.ok?"Booking cancelled":"Could not cancel the booking")}
            catch(e){toast("Cannot reach the server")}
            loadMine();loadRooms();
        }

        /* ---------- feedback (reviews) ---------- */
        function stars(n){n=Math.max(0,Math.min(5,Number(n)||0));return "★★★★★".slice(0,n)+"☆☆☆☆☆".slice(0,5-n)}
        function revId(r){return r.id!=null?r.id:r.reviewId}
        function revDate(r){return String(r.date||r.Date||"")}
        function revRoomId(r){return r.room&&r.room.roomId}
        function revUserId(r){return r.user&&r.user.id}
        function reviewsFor(roomId){
            return state.reviews.filter(function(r){return revRoomId(r)===roomId}).sort(function(a,b){return (revId(b)||0)-(revId(a)||0)});
        }
        function revText(roomId){
            var l=reviewsFor(roomId);
            if(!l.length)return "No reviews yet";
            var sum=0;l.forEach(function(r){sum+=Number(r.rating)||0});
            return "★ "+(sum/l.length).toFixed(1)+" · "+l.length+(l.length===1?" review":" reviews");
        }
        async function loadReviews(){
            try{var res=await fetch(REV+"/reviews");if(res.ok)state.reviews=await res.json()}catch(e){}
            renderRooms();
        }

        /* ---------- payments ---------- */
        function payFor(bookingId){
            for(var i=0;i<state.pays.length;i++){var p=state.pays[i];if(p.booking&&String(p.booking.bookingId)===String(bookingId))return p}
            return null;
        }
        function niceStatus(v){v=String(v||"pending").toLowerCase();return v.charAt(0).toUpperCase()+v.slice(1)}
        async function loadPays(){
            state.pays=[];
            if(!state.user)return;
            try{
                var res=await fetch(PAY+"/payments");
                if(res.ok){var all=await res.json(),uid=String(state.user.id);state.pays=all.filter(function(p){return p.user&&String(p.user.id)===uid})}
            }catch(e){}
            if(state.pop)renderPop();
        }
        async function removePay(id){
            if(!confirm("Remove this payment record?"))return;
            try{var res=await fetch(PAY+"/"+id,{method:"DELETE"});toast(res.ok?"Payment removed":"Could not remove the payment")}
            catch(e){toast("Cannot reach the server")}
            loadPays();
        }

        /* ---------- nav auth ---------- */
        var authEl=document.getElementById("auth");
        function renderAuth(){
            authEl.textContent="";
            if(!state.user){
                state.pop=false;
                authEl.appendChild(h("button",{class:"btn",type:"button",id:"loginBtn",text:"Login",onclick:function(){openModal("login")}}));
                return;
            }
            var av=h("button",{class:"avatar",type:"button",id:"avatarBtn","aria-haspopup":"true","aria-expanded":String(state.pop),"aria-label":"Account menu for "+state.user.userName,text:state.user.userName.charAt(0).toUpperCase(),
                onclick:function(e){e.stopPropagation();state.pop=!state.pop;state.confirmDel=false;state.editPhone=false;renderAuth()}});
            authEl.appendChild(av);
            if(state.pop){authEl.appendChild(h("div",{class:"pop",id:"pop",onclick:function(e){e.stopPropagation()}}));renderPop()}
        }
        function renderPop(){
            var pop=document.getElementById("pop");if(!pop)return;
            var u=state.user;pop.textContent="";
            pop.appendChild(h("h3",{text:u.userName}));
            pop.appendChild(h("p",{class:"sub",text:"Customer account #"+u.id}));

            var phoneCell;
            if(state.editPhone){
                var pin=h("input",{type:"tel",id:"newphone",value:u.phoneNumber||"",style:"padding:6px 8px;font-size:12px"});
                var perr=h("p",{class:"err",style:"margin:6px 0 0"});
                phoneCell=h("dd",null,pin,
                    h("div",{class:"row",style:"margin-top:6px"},
                        h("button",{type:"button",text:"Save",onclick:async function(){
                                var v=pin.value.trim(),d=v.replace(/\D/g,"");
                                if(!/^\+?[0-9 ()-]{7,20}$/.test(v)||d.length<7||d.length>15){perr.textContent="Enter a valid phone number.";return}
                                try{
                                    var res=await fetch(CUST+"/"+u.id,{method:"PUT",headers:{"Content-Type":"application/json"},body:JSON.stringify({phoneNumber:v})});
                                    if(!res.ok){perr.textContent="Could not update (server "+res.status+").";return}
                                    state.user=await res.json();state.editPhone=false;renderPop();toast("Phone number updated");loadMine();
                                }catch(e){perr.textContent="Cannot reach the server."}
                            }}),
                        h("button",{type:"button",text:"Cancel",onclick:function(){state.editPhone=false;renderPop()}})),
                    perr);
            }else{
                phoneCell=h("dd",null,u.phoneNumber||"—",h("button",{type:"button",text:"Edit",onclick:function(){state.editPhone=true;renderPop()}}));
            }
            pop.appendChild(h("dl",{class:"dl"},h("dt",{text:"Username"}),h("dd",{text:u.userName}),h("dt",{text:"Phone"}),phoneCell));

            var active=state.mine.filter(function(b){return b.status==="Confirmed"});
            var box=h("div",{class:"res"},h("b",{text:"Your bookings"}));
            if(!active.length)box.appendChild(h("span",{text:"None yet"}));
            active.forEach(function(b){
                var p=payFor(b.bookingId);
                var btns=h("div",{class:"bkbtns"});
                if(p){
                    btns.appendChild(h("span",{class:"paytag",text:"Payment: "+niceStatus(p.status)}));
                    btns.appendChild(h("button",{type:"button",class:"btn ghost",text:"Card",onclick:function(){openModal("pay",{booking:b,pay:p})}}));
                    btns.appendChild(h("button",{type:"button",class:"btn ghost",text:"Remove payment",onclick:function(){removePay(p.paymentId)}}));
                }else{
                    btns.appendChild(h("button",{type:"button",class:"btn",text:"Pay now",onclick:function(){openModal("pay",{booking:b})}}));
                }
                btns.appendChild(h("button",{type:"button",class:"btn ghost",text:"Cancel booking",onclick:function(){cancelMine(b.bookingId)}}));
                box.appendChild(h("div",{class:"bk"},
                    h("span",{text:"Room "+b.roomId+" · "+b.checkIn+" to "+b.checkOut+" · Rs. "+b.totalPrice}),
                    btns));
            });
            pop.appendChild(box);
            if(!state.confirmDel){
                pop.appendChild(h("div",{class:"row"},
                    h("button",{class:"btn ghost",type:"button",text:"Log out",onclick:logout}),
                    h("button",{class:"btn danger",type:"button",text:"Delete account",onclick:function(){state.confirmDel=true;renderPop()}})
                ));
            }else{
                var pw=h("input",{type:"password",id:"delpw",placeholder:"Enter your password",autocomplete:"current-password"});
                var err=h("p",{class:"err",style:"margin:8px 0 0"});
                pop.appendChild(h("div",{class:"confirm"},
                    h("p",{text:"This permanently removes your account and cancels your bookings. Enter your password to confirm."}),
                    pw,
                    h("div",{class:"row",style:"margin-top:12px"},
                        h("button",{class:"btn danger",type:"button",text:"Delete permanently",onclick:async function(){
                                try{
                                    var ok=await apiLogin(u.userName,pw.value);          // re-check the password on the backend
                                    if(!ok){err.textContent="That password is not correct.";return}
                                    var active2=state.mine.filter(function(b){return b.status==="Confirmed"});
                                    for(var i=0;i<active2.length;i++){try{await fetch(API+"/bookings/"+active2[i].bookingId+"/cancel",{method:"PUT"})}catch(e){}}
                                    for(var j=0;j<state.pays.length;j++){try{await fetch(PAY+"/"+state.pays[j].paymentId,{method:"DELETE"})}catch(e){}}
                                    var myRevs=state.reviews.filter(function(r){return String(revUserId(r))===String(u.id)});
                                    for(var k=0;k<myRevs.length;k++){try{await fetch(REV+"/"+revId(myRevs[k]),{method:"DELETE"})}catch(e){}}
                                    var res=await fetch(CUST+"/"+u.id,{method:"DELETE"});
                                    if(!res.ok){err.textContent="Could not delete the account (server "+res.status+").";return}
                                    lsSet(SESSION_KEY,"");state.user=null;state.mine=[];state.pays=[];state.pop=false;state.confirmDel=false;
                                    renderAuth();renderRooms();loadRooms();loadReviews();toast("Your account has been deleted");
                                }catch(e){err.textContent="Cannot reach the server. Is the backend running?"}
                            }}),
                        h("button",{class:"btn ghost",type:"button",text:"Keep account",onclick:function(){state.confirmDel=false;renderPop()}})
                    ),err
                ));
            }
        }
        function logout(){lsSet(SESSION_KEY,"");state.user=null;state.mine=[];state.pays=[];state.pop=false;state.confirmDel=false;state.editPhone=false;renderAuth();renderRooms();toast("Signed out")}
        document.addEventListener("click",function(){if(state.pop){state.pop=false;state.confirmDel=false;state.editPhone=false;renderAuth()}});
        document.addEventListener("keydown",function(e){
            if(e.key!=="Escape")return;
            if(document.getElementById("back"))closeModal();
            else if(state.pop){state.pop=false;state.confirmDel=false;state.editPhone=false;renderAuth();var b=document.getElementById("avatarBtn");if(b)b.focus()}
        });

        /* ---------- modal ---------- */
        var lastFocus=null;
        function closeModal(){var b=document.getElementById("back");if(b){b.remove();document.body.style.overflow="";if(lastFocus&&lastFocus.focus)lastFocus.focus()}}
        function field(id,label,attrs,full){
            var el=h("input",Object.assign({id:id,name:id,required:"required"},attrs));
            return h("div",{class:"f"+(full?" full":"")},h("label",{for:id,text:label}),el);
        }
        function val(id){return document.getElementById(id).value.trim()}
        function openModal(view,note){
            closeModal();
            lastFocus=document.activeElement;
            var back=h("div",{class:"back",id:"back",onclick:function(e){if(e.target===back)closeModal()}});
            var box=h("div",{class:"modal",role:"dialog","aria-modal":"true","aria-labelledby":"mt"});
            box.appendChild(h("button",{class:"x",type:"button","aria-label":"Close",text:"×",onclick:closeModal}));
            if(view==="login")buildLogin(box,note);else if(view==="book")buildBook(box,note);else if(view==="pay")buildPay(box,note);else if(view==="reviews")buildReviews(box,note);else buildSignup(box);
            back.appendChild(box);document.body.appendChild(back);document.body.style.overflow="hidden";
            var first=box.querySelector("input");if(first)first.focus();
        }
        function buildBook(box,room){
            var u=state.user;
            box.appendChild(h("h2",{id:"mt",text:"Book room "+room.id}));
            box.appendChild(h("p",{class:"sub",text:room.type+" Room · Rs. "+room.price+" per night · for "+u.userName}));
            var err=h("p",{class:"err",id:"berr",role:"alert"});
            var d=new Date();d.setMinutes(d.getMinutes()-d.getTimezoneOffset());
            var today=d.toISOString().slice(0,10);
            var form=h("form",{novalidate:"novalidate"},
                field("b-in","Check-in",{type:"date",min:today,value:(window.__stay&&window.__stay.in)||""}),
                field("b-out","Check-out",{type:"date",min:today,value:(window.__stay&&window.__stay.out)||""}),
                err,
                h("button",{class:"btn solid full",type:"submit",style:"grid-column:1/-1",text:"Confirm booking"})
            );
            form.addEventListener("submit",async function(e){
                e.preventDefault();err.textContent="";
                var cin=val("b-in"),cout=val("b-out");
                if(!cin||!cout){err.textContent="Choose your check-in and check-out dates.";return}
                if(cout<=cin){err.textContent="Check-out must be after check-in.";return}
                try{
                    var res=await fetch(API+"/bookings",{method:"POST",headers:{"Content-Type":"application/json"},
                        body:JSON.stringify({roomId:room.id,guestName:u.userName,guestPhone:u.phoneNumber,checkIn:cin,checkOut:cout,totalPrice:0})});
                    var text=await res.text();
                    if(!res.ok){err.textContent=errText(text)||"The booking could not be made. The room may already be taken for those dates.";return}
                    var bid=null,total=0,j=null;
                    try{j=JSON.parse(text)}catch(e){}
                    if(j&&typeof j==="object"){bid=j.bookingId!=null?j.bookingId:j.id;total=Number(j.totalPrice)||0}
                    if(bid==null){var idm=text.match(/ID:?\s*(\d+)/i);if(idm)bid=parseInt(idm[1],10)}
                    if(!total){var tm=text.match(/total\s*price:?\s*(?:Rs\.?\s*)?([\d.]+)/i);if(tm)total=parseFloat(tm[1])}
                    await loadMine();
                    if(bid==null){
                        var m=state.mine.filter(function(b){return b.roomId===room.id&&String(b.checkIn)===cin&&String(b.checkOut)===cout&&b.status==="Confirmed"}).sort(function(x,y){return y.bookingId-x.bookingId})[0];
                        if(m){bid=m.bookingId;if(!total)total=Number(m.totalPrice)||0}
                    }
                    if(!total)total=nightsBetween(cin,cout)*room.price;
                    toast("Room "+room.id+" booked · Rs. "+total);loadRooms();
                    if(bid!=null)openModal("pay",{booking:{bookingId:bid,roomId:room.id,checkIn:cin,checkOut:cout,totalPrice:total}});
                    else closeModal();
                }catch(ex){err.textContent="Cannot reach the server. Is the backend running?"}
            });
            box.appendChild(form);
        }
        function buildPay(box,ctx){
            var b=ctx.booking,u=state.user,p=ctx.pay||payFor(b.bookingId),editing=!!p;
            box.appendChild(h("h2",{id:"mt",text:editing?"Card details":"Pay for your stay"}));
            box.appendChild(h("p",{class:"sub",text:"Booking #"+b.bookingId+" · Room "+b.roomId+" · "+b.checkIn+" to "+b.checkOut+" · Rs. "+b.totalPrice}));
            var err=h("p",{class:"err",id:"perr",role:"alert"});
            var go=h("button",{class:"btn solid full",type:"submit",style:"grid-column:1/-1",text:editing?"Save card details":"Pay Rs. "+b.totalPrice});
            var form=h("form",{novalidate:"novalidate"},
                field("p-name","Card holder name",{type:"text",autocomplete:"cc-name",value:editing?(p.cardHolder||""):""},true),
                field("p-cvv",editing?"CVV (leave blank to keep the current one)":"CVV",{type:"password",inputmode:"numeric",maxlength:"4",autocomplete:"cc-csc",placeholder:"123"},true),
                err,go);
            if(!editing)form.appendChild(h("button",{class:"btn ghost full",type:"button",style:"grid-column:1/-1",text:"Pay later",onclick:closeModal}));
            form.addEventListener("submit",async function(e){
                e.preventDefault();err.textContent="";
                var name=val("p-name"),cv=val("p-cvv"),cvOk=/^\d{3,4}$/.test(cv);
                if(!/^[A-Za-z][A-Za-z .'-]{1,59}$/.test(name)){err.textContent="Enter the card holder name as printed on the card.";return}
                if((!editing&&!cvOk)||(editing&&cv&&!cvOk)){err.textContent="The CVV must be 3 or 4 digits.";return}
                if(!editing&&payFor(b.bookingId)){err.textContent="This booking already has a payment.";return}
                go.disabled=true;
                try{
                    var res;
                    if(editing){
                        var upd={cardHolder:name};if(cv)upd.cardCvvNumber=cv;
                        res=await fetch(PAY+"/"+p.paymentId,{method:"PUT",headers:JSONH,body:JSON.stringify(upd)});
                    }else{
                        res=await fetch(PAY+"/newpay",{method:"POST",headers:JSONH,body:JSON.stringify({customerId:u.id,bookingId:b.bookingId,cardHolderName:name,cardCvvNumber:cv})});
                    }
                    if(!res.ok){err.textContent="The payment could not be saved (server "+res.status+").";go.disabled=false;return}
                    closeModal();toast(editing?"Card details updated":"Payment received for booking #"+b.bookingId);loadPays();
                }catch(ex){err.textContent="Cannot reach the server. Is the backend running?";go.disabled=false}
            });
            box.appendChild(form);
        }
        async function canReviewRoom(room){
            try{var res=await fetch(REV+"/can-review?customerId="+encodeURIComponent(state.user.id)+"&roomId="+encodeURIComponent(room.id));return res.ok&&(await res.text()).trim()==="true"}catch(e){return false}
        }
        function buildReviews(box,room){
            var u=state.user,list=reviewsFor(room.id);
            var mine=u?list.filter(function(r){return String(revUserId(r))===String(u.id)})[0]:null;
            box.appendChild(h("h2",{id:"mt",text:"Room "+room.id+" reviews"}));
            box.appendChild(h("p",{class:"sub",text:list.length?revText(room.id):"No reviews yet."}));
            if(list.length){
                var wrap=h("div",{class:"rvlist"});
                list.forEach(function(r){
                    wrap.appendChild(h("div",{class:"rv"},
                        h("div",{class:"rvtop"},h("span",{class:"rvstars",text:stars(r.rating)}),h("span",{text:((r.user&&r.user.userName)||"Guest")+(revDate(r)?" · "+revDate(r):"")})),
                        h("p",{text:r.comment||""})));
                });
                box.appendChild(wrap);
            }
            if(!u){
                box.appendChild(h("button",{class:"btn solid full",type:"button",style:"width:100%",text:"Sign in to leave a review",onclick:function(){openModal("login","Sign in to leave a review.")}}));
                return;
            }
            if(!mine){
                var gate=h("p",{class:"sub",text:"Checking if you can review this room…"});
                box.appendChild(gate);
                canReviewRoom(room).then(function(ok){
                    if(!document.body.contains(gate))return;
                    if(ok){gate.remove();buildReviewForm(box,room,u,mine)}
                    else gate.textContent="Only guests who have paid for and checked in to this room can leave a review.";
                });
                return;
            }
            buildReviewForm(box,room,u,mine);
        }
        function buildReviewForm(box,room,u,mine){
            var err=h("p",{class:"err",id:"rerr",role:"alert"});
            var sel=h("select",{id:"r-rating"},
                h("option",{value:"5",text:"5 · Excellent"}),h("option",{value:"4",text:"4 · Very good"}),h("option",{value:"3",text:"3 · Good"}),
                h("option",{value:"2",text:"2 · Fair"}),h("option",{value:"1",text:"1 · Poor"}));
            var ta=h("textarea",{id:"r-comment",maxlength:"300",placeholder:"How was your stay?"});
            if(mine){sel.value=String(mine.rating);ta.value=mine.comment||""}
            var save=h("button",{class:"btn solid full",type:"submit",style:"grid-column:1/-1",text:mine?"Update my review":"Post review"});
            var form=h("form",{novalidate:"novalidate"},
                h("div",{class:"f full"},h("label",{for:"r-rating",text:mine?"Your rating (you already reviewed this room)":"Your rating"}),sel),
                h("div",{class:"f full"},h("label",{for:"r-comment",text:"Your comment"}),ta),
                err,save);
            if(mine)form.appendChild(h("button",{class:"btn danger full",type:"button",style:"grid-column:1/-1",text:"Delete my review",onclick:async function(){
                    if(!confirm("Delete your review?"))return;
                    try{
                        var res=await fetch(REV+"/"+revId(mine),{method:"DELETE"});
                        if(!res.ok){err.textContent="Could not delete the review (server "+res.status+").";return}
                        await loadReviews();toast("Review deleted");openModal("reviews",room);
                    }catch(ex){err.textContent="Cannot reach the server."}
                }}));
            form.addEventListener("submit",async function(e){
                e.preventDefault();err.textContent="";
                var rating=parseInt(sel.value,10),comment=ta.value.trim();
                if(!(rating>=1&&rating<=5)){err.textContent="Choose a rating from 1 to 5.";return}
                if(comment.length<3){err.textContent="Write a few words about your stay.";return}
                if(comment.length>300){err.textContent="Keep your comment under 300 characters.";return}
                save.disabled=true;
                try{
                    var res=mine
                        ?await fetch(REV+"/"+revId(mine),{method:"PUT",headers:JSONH,body:JSON.stringify({rating:rating,comment:comment})})
                        :await fetch(REV+"/newreview",{method:"POST",headers:JSONH,body:JSON.stringify({customerId:u.id,roomId:room.id,rating:rating,comment:comment})});
                    if(res.status===403){err.textContent="Only guests who have paid for and checked in can leave a review.";save.disabled=false;return}
                    if(!res.ok){err.textContent="Could not save your review (server "+res.status+").";save.disabled=false;return}
                    await loadReviews();toast(mine?"Review updated":"Thank you for your review");openModal("reviews",room);
                }catch(ex){err.textContent="Cannot reach the server. Is the backend running?";save.disabled=false}
            });
            box.appendChild(form);
        }
        function buildLogin(box,note){
            box.appendChild(h("h2",{id:"mt",text:"Welcome back"}));
            box.appendChild(h("p",{class:"sub",text:note||"Sign in to manage your stay."}));
            var err=h("p",{class:"err",id:"lerr",role:"alert"});
            var form=h("form",{novalidate:"novalidate"},
                field("l-user","Username",{type:"text",autocomplete:"username"},true),
                field("l-pw","Password",{type:"password",autocomplete:"current-password"},true),
                err,
                h("button",{class:"btn solid full",type:"submit",style:"grid-column:1/-1",text:"Login"}),
                h("div",{class:"swap"},h("span",{text:"New to NOVA?"}),h("button",{class:"btn",type:"button",id:"toSignup",text:"Sign up",onclick:function(){openModal("signup")}}))
            );
            form.addEventListener("submit",async function(e){
                e.preventDefault();err.textContent="";
                var name=val("l-user"),pw=document.getElementById("l-pw").value;
                if(!name||!pw){err.textContent="Enter your username and password.";return}
                try{
                    var u=await apiLogin(name,pw);
                    if(!u){err.textContent="Username or password is incorrect.";return}
                    startSession(u);closeModal();toast("Welcome back, "+u.userName);
                }catch(ex){err.textContent="Cannot reach the server. Is the backend running?"}
            });
            box.appendChild(form);
        }
        function buildSignup(box){
            box.appendChild(h("h2",{id:"mt",text:"Create your account"}));
            box.appendChild(h("p",{class:"sub",text:"Choose a username and password to book a room."}));
            var err=h("p",{class:"err",id:"serr",role:"alert"});
            var form=h("form",{novalidate:"novalidate"},
                field("s-user","Username",{type:"text",autocomplete:"username"},true),
                field("s-pw","Password (6+ characters)",{type:"password",autocomplete:"new-password",minlength:"6"},true),
                field("s-phone","Phone number",{type:"tel",autocomplete:"tel",placeholder:"0771234567"},true),
                err,
                h("button",{class:"btn solid full",type:"submit",style:"grid-column:1/-1",text:"Create account"}),
                h("div",{class:"swap"},h("span",{text:"Already a guest?"}),h("button",{class:"btn ghost",type:"button",text:"Back to login",onclick:function(){openModal("login")}}))
            );
            form.addEventListener("submit",async function(e){
                e.preventDefault();err.textContent="";
                var d={userName:val("s-user"),pw:document.getElementById("s-pw").value,phone:val("s-phone")};
                var digits=d.phone.replace(/\D/g,"");
                var msg=
                    !/^[A-Za-z0-9_.-]{3,30}$/.test(d.userName)?"Username must be 3 to 30 letters, digits, or . _ - with no spaces.":
                        d.pw.length<6?"Your password needs at least 6 characters.":
                            !/^\+?[0-9 ()-]{7,20}$/.test(d.phone)||digits.length<7||digits.length>15?"Enter a valid phone number.":"";
                if(msg){err.textContent=msg;return}
                try{
                    if(await usernameTaken(d.userName)){err.textContent="That username is already taken. Choose another or log in.";return}
                    var u=await apiRegister(d.userName,d.pw,d.phone);
                    startSession(u);closeModal();toast("Account created. Welcome, "+u.userName);
                }catch(ex){err.textContent="Cannot reach the server. Is the backend running?"}
            });
            box.appendChild(form);
        }
        function startSession(u){lsSet(SESSION_KEY,u.id);state.user=u;state.pop=false;state.editPhone=false;renderAuth();renderRooms();loadMine();loadPays()}

        /* ---------- boot ---------- */
        renderRooms();renderAuth();loadRooms();loadReviews();
        (async function(){
            var id=lsGet(SESSION_KEY,"");
            if(!id)return;
            try{var u=await apiGetCustomer(id);if(u){state.user=u;renderAuth();loadMine();loadPays()}else lsSet(SESSION_KEY,"")}catch(e){}
        })();
    })();