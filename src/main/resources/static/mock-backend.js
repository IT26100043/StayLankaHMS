/* Stand-in for the Spring Boot backend, for trying the site without it.
   Run:  node mock-backend.js   then open http://localhost:8080
   Same routes as the real API. Data lives in memory only. */
const http=require("http"),fs=require("fs"),path=require("path");
const PORT=process.env.PORT||8080,ROOT=__dirname;
let rooms=[{roomId:1,type:"Standard",capacity:2,pricePerNight:8500},{roomId:2,type:"Standard",capacity:2,pricePerNight:9000},{roomId:3,type:"Deluxe",capacity:3,pricePerNight:14000},{roomId:4,type:"Suite",capacity:4,pricePerNight:26000}];
let customers=[],bookings=[],payments=[],reviews=[],ids={c:1,b:1,p:1,r:1};
const MIME={".html":"text/html",".css":"text/css",".js":"text/javascript",".jpg":"image/jpeg",".png":"image/png",".woff2":"font/woff2",".woff":"font/woff",".ttf":"font/ttf",".svg":"image/svg+xml"};
function send(res,code,body,type){res.writeHead(code,{"Content-Type":type||"application/json","Access-Control-Allow-Origin":"*","Access-Control-Allow-Methods":"GET,POST,PUT,DELETE,OPTIONS","Access-Control-Allow-Headers":"Content-Type"});res.end(body==null?"":(typeof body==="string"||Buffer.isBuffer(body))?body:JSON.stringify(body))}
const json=(res,o,c)=>send(res,c||200,o);
const strip=c=>c&&{id:c.id,userName:c.userName,phoneNumber:c.phoneNumber};
http.createServer((req,res)=>{
  const u=new URL(req.url,"http://x"),p=u.pathname,m=req.method;let raw="";
  req.on("data",d=>raw+=d);req.on("end",()=>{
    let b={};try{b=raw?JSON.parse(raw):{}}catch(e){}
    if(m==="OPTIONS")return send(res,204,"");
    let x;
    if(p==="/api/v1/rooms"&&m==="GET")return json(res,rooms);
    if(p==="/api/v1/bookings"&&m==="GET"){const k=(u.searchParams.get("keyword")||"").toLowerCase();return json(res,bookings.filter(t=>!k||t.guestName.toLowerCase().includes(k)))}
    if(p==="/api/v1/bookings"&&m==="POST"){
      const r=rooms.find(t=>t.roomId===b.roomId);if(!r)return send(res,404,"Room not found","text/plain");
      if(!(b.checkOut>b.checkIn))return send(res,400,"Check-out must be after check-in","text/plain");
      if(bookings.some(t=>t.roomId===b.roomId&&t.status==="Confirmed"&&b.checkIn<t.checkOut&&b.checkOut>t.checkIn))return send(res,409,"Room "+b.roomId+" is already booked for those dates","text/plain");
      const n=Math.round((new Date(b.checkOut)-new Date(b.checkIn))/864e5),bk={bookingId:ids.b++,roomId:b.roomId,guestName:b.guestName,guestPhone:b.guestPhone,checkIn:b.checkIn,checkOut:b.checkOut,totalPrice:n*r.pricePerNight,status:"Confirmed"};
      bookings.push(bk);return send(res,200,"Booking confirmed. ID: "+bk.bookingId+", total price: "+bk.totalPrice,"text/plain")}
    if((x=p.match(/^\/api\/v1\/bookings\/(\d+)\/cancel$/))&&m==="PUT"){const t=bookings.find(q=>q.bookingId==x[1]);if(!t)return send(res,404,"Not found","text/plain");t.status="Cancelled";return send(res,200,"Cancelled","text/plain")}
    if(p==="/auth/customer"&&m==="POST"){const c=customers.find(t=>t.userName===b.userName&&t.password===b.password);return c?json(res,strip(c)):send(res,200,"","text/plain")}
    if(p==="/customer/register"&&m==="POST"){const c={id:ids.c++,userName:b.userName,password:b.password,phoneNumber:b.phoneNumber};customers.push(c);return json(res,strip(c))}
    if(p==="/customer/customers"&&m==="GET")return json(res,customers.map(strip));
    if((x=p.match(/^\/customer\/(\d+)$/))){const c=customers.find(t=>t.id==x[1]);
      if(m==="GET")return c?json(res,strip(c)):send(res,200,"","text/plain");
      if(!c)return send(res,404,"Not found","text/plain");
      if(m==="PUT"){if(b.phoneNumber)c.phoneNumber=b.phoneNumber;return json(res,strip(c))}
      if(m==="DELETE"){customers=customers.filter(t=>t!==c);return send(res,200,"","text/plain")}}
    if(p==="/payment/newpay"&&m==="POST"){const c=customers.find(t=>t.id==b.customerId),bk=bookings.find(t=>t.bookingId==b.bookingId);if(!c||!bk)return send(res,400,"Bad request","text/plain");
      const pay={paymentId:ids.p++,booking:{bookingId:bk.bookingId},user:{id:c.id},cardHolder:b.cardHolderName,cvv:b.cardCvvNumber,status:"paid"};payments.push(pay);return json(res,pay)}
    if(p==="/payment/payments"&&m==="GET")return json(res,payments.map(({cvv,...r})=>r));
    if((x=p.match(/^\/payment\/(\d+)$/))){const t=payments.find(q=>q.paymentId==x[1]);if(!t)return send(res,404,"Not found","text/plain");
      if(m==="PUT"){if(b.cardHolder)t.cardHolder=b.cardHolder;if(b.cardCvvNumber)t.cvv=b.cardCvvNumber;return json(res,t)}
      if(m==="DELETE"){payments=payments.filter(q=>q!==t);return send(res,200,"","text/plain")}}
    if(p==="/review/reviews"&&m==="GET")return json(res,reviews);
    if(p==="/review/can-review"&&m==="GET"){const cid=u.searchParams.get("customerId"),rid=u.searchParams.get("roomId");
      const c=customers.find(t=>t.id==cid);const ok=!!c&&payments.some(pay=>pay.user.id==c.id&&bookings.some(bk=>bk.bookingId==pay.booking.bookingId&&String(bk.roomId)===rid));return send(res,200,String(ok),"text/plain")}
    if(p==="/review/newreview"&&m==="POST"){const c=customers.find(t=>t.id==b.customerId);if(!c)return send(res,403,"Forbidden","text/plain");
      const ok=payments.some(pay=>pay.user.id==c.id&&bookings.some(bk=>bk.bookingId==pay.booking.bookingId&&bk.roomId==b.roomId));if(!ok)return send(res,403,"Forbidden","text/plain");
      const r={id:ids.r++,rating:b.rating,comment:b.comment,date:new Date().toISOString().slice(0,10),user:{id:c.id,userName:c.userName},room:{roomId:b.roomId}};reviews.push(r);return json(res,r)}
    if((x=p.match(/^\/review\/(\d+)$/))){const r=reviews.find(q=>q.id==x[1]);if(!r)return send(res,404,"Not found","text/plain");
      if(m==="PUT"){r.rating=b.rating;r.comment=b.comment;return json(res,r)}
      if(m==="DELETE"){reviews=reviews.filter(q=>q!==r);return send(res,200,"","text/plain")}}
    /* static files */
    let f=path.join(ROOT,p==="/"?"index.html":decodeURIComponent(p));
    if(!f.startsWith(ROOT)||!fs.existsSync(f)||fs.statSync(f).isDirectory())return send(res,404,"Not found","text/plain");
    send(res,200,fs.readFileSync(f),MIME[path.extname(f)]||"application/octet-stream");
  })
}).listen(PORT,()=>console.log("Mock hotel backend + site on http://localhost:"+PORT));
