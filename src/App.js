import "./App.css";
import { Route, Routes } from "react-router-dom";
import AboutUs from "./page/AboutUs";
import ContactUs from "./page/ContactUs";
import Header from "./NavbarComponent/Header";
import HomePage from "./page/HomePage";
import UserRegister from "./UserComponent/UserRegister";
import UserLoginForm from "./UserComponent/UserLoginForm";
import AddSportsForm from "./SportsComponent/AddSportsForm";
import ViewAllCustomer from "./UserComponent/ViewAllCusomer";
import Sports from "./SportsComponent/Sports";
import ViewAllCoach from "./UserComponent/ViewAllCoach";
import ViewMyBooking from "./BookingComponent/ViewMyBooking";
import ViewAllBooking from "./BookingComponent/ViewAllBooking";
import VerifyBooking from "./BookingComponent/VerifyBooking";
import ViewMyAllBookings from "./BookingComponent/ViewMyAllBookings";

function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/home/all/hotel/location" element={<HomePage />} />

        <Route path="contact" element={<ContactUs />} />
        <Route path="about" element={<AboutUs />} />

        <Route path="user/coach/register" element={<UserRegister />} />
        <Route path="user/customer/register" element={<UserRegister />} />
        <Route path="user/admin/register" element={<UserRegister />} />
        <Route path="/user/login" element={<UserLoginForm />} />

        <Route path="admin/sports/add" element={<AddSportsForm />} />
        {/* <Route path="book/sports/add" element={<AddGroundForm />} /> */}
        <Route path="user/customer/all" element={<ViewAllCustomer />} />
        <Route path="user/coach/all" element={<ViewAllCoach />} />

        <Route
          path="/book/sports/:sportsId/coach/:coachId"
          element={<Sports />}
        />
        <Route path="user/sports/bookings" element={<ViewMyBooking />} />
        <Route path="user/sports/booking/all" element={<ViewAllBooking />} />
        <Route
          path="user/sports/customer/bookings"
          element={<ViewMyAllBookings />}
        />
        <Route
          path="/user/coach/verify/booking/:bookingId"
          element={<VerifyBooking />}
        />

        {/* <Route path="user/admin/booking/all" element={<ViewAllBooking />} />
        <Route path="user/hotel/bookings" element={<ViewMyBooking />} />
         */}
      </Routes>
    </div>
  );
}

export default App;
