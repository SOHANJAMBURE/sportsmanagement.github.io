import AdminHeader from "./AdminHeader";
import CoachHeader from "./CoachHeader";
import CustomerHeader from "./CustomerHeader";
import NormalHeader from "./NormalHeader";

const RoleNav = () => {
  const user = JSON.parse(sessionStorage.getItem("active-customer"));
  const admin = JSON.parse(sessionStorage.getItem("active-admin"));
   const coach = JSON.parse(sessionStorage.getItem("active-coach"));


  if (user != null) {
    return <CustomerHeader />;
  } else if (admin != null) {
    return <AdminHeader />;
  } else if (coach != null) {
    return <CoachHeader />;
  }
   else {
    return <NormalHeader />;
  }
};

export default RoleNav;
