NOVA Hotel site (Keto template + your Spring Boot backend)

OPTION A - with your backend (recommended)
  Copy everything in this folder (index.html, css, js, images, fonts) into
  src/main/resources/static/ of the Spring Boot project, run the app, open http://localhost:8080
  The page then calls the API on the same address, so no CORS setup is needed.

OPTION B - open the page separately (Live Server, file, other port)
  The page calls http://localhost:8080. Your Spring Boot controllers need @CrossOrigin
  (or a global CORS config) for GET, POST, PUT, DELETE.

TRY IT WITHOUT THE BACKEND
  node mock-backend.js    then open http://localhost:8080
  (in-memory demo data; same routes as the real API)

API routes used (same as your original page)
  GET  /api/v1/rooms                      GET/POST /api/v1/bookings   PUT /api/v1/bookings/{id}/cancel
  POST /auth/customer                     POST /customer/register     GET /customer/customers
  GET/PUT/DELETE /customer/{id}           POST /payment/newpay        GET /payment/payments
  PUT/DELETE /payment/{id}                GET /review/reviews         POST /review/newreview
  GET /review/can-review                  PUT/DELETE /review/{id}

Photos: replace files in images/ (room1-6.jpg, banner1-3.jpg, gallery1-8.jpg, about.png).
