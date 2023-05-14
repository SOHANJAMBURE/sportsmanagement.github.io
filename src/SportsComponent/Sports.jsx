import { useParams } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import SportsCard from "./SportsCard";

const Sports = () => {
  const { sportsId, coachId } = useParams();

  let user = JSON.parse(sessionStorage.getItem("active-customer"));
  let admin = JSON.parse(sessionStorage.getItem("active-admin"));

  const [batch, setBatch] = useState([]);

  const [sports, setSports] = useState([]);
  const [coach, setCoach] = useState([]);

  let navigate = useNavigate();

  const [sport, setSport] = useState({
    id: "",
    name: "",
    description: "",
    price: "",
    image:"",
    coachId:""
  });

  const [booking, setBooking] = useState({
    userId: "",
    sportsId: "",
    date: "",
    batch: "",
  });

  const handleBookingInput = (e) => {
    setBooking({ ...booking, [e.target.name]: e.target.value });
  };

  const retrieveAllBatch= async () => {
    const response = await axios.get("http://localhost:8585/api/book/sports/fetch/batch");
    return response.data;
  };

  useEffect(() => {
    const getAllSports = async () => {
      const allSports = await retrieveAllSports();
      if (allSports) {
        setSports(allSports);
      }
    };

    getAllSports();

  }, []);
  
  const retrieveAllSports = async () => {
    const response = await axios.get("http://localhost:8585/api/sports/fetch");

    return response.data;
  };


  const retrieveSports = async () => {
    const response = await axios.get(
      "http://localhost:8585/api/sports/id?sportsId=" + sportsId
    );

    return response.data;
  };

  const retrieveCoach = async () => {
    const response = await axios.get(
      "http://localhost:8585/api/user/id?userId=" + coachId
    );

    return response.data;
  };

  useEffect(() => {
    const getSports = async () => {
      const retrievedSport = await retrieveSports();

      setSport(retrievedSport);
    };

    
    const getAllBatch= async () => {
      const allBatch = await retrieveAllBatch();
      if (allBatch) {
        setBatch(allBatch);
      }
    };

    const getCoach = async () => {
      const retrievedCoach = await retrieveCoach();
      setCoach(retrievedCoach.user);
    };

    getSports();
    getAllBatch();
    getCoach();
  }, [sportsId]);

  const bookSport = (e) => {

    if(user == null) {
      alert("Please login to book the Sports!!!");
      e.preventDefault();
    } else {
      const formData = new FormData();
    formData.append("userId", user.id);
    formData.append("sportsId", sportsId);
    formData.append("date", booking.date);
    formData.append("batch", booking.batch);

    console.log(formData)

    axios
      .post("http://localhost:8585/api/book/sports/", formData)
      .then((result) => {
        result.json().then((res) => {
          console.log(res);
          console.log(res.responseMessage);
          alert("Sports Booked Successfully!!!")
        });
        
      });
    }
  };

  return (
    <div className="container-fluid mb-5">
      <div class="row">
        
        <div class="col-sm-3 mt-2">
          <div class="card form-card border-color custom-bg">
            <img
              src={"http://localhost:8585/api/sports/" + sport.image}
              style={{
                maxHeight: "500px",
                maxWidth: "100%",
                width: "auto",
              }}
              class="card-img-top rounded mx-auto d-block m-2"
              alt="img"
            />
          </div>
        </div>
        <div class="col-sm-5 mt-2">
          <div class="card form-card border-color custom-bg">
            <div class="card-header bg-color">
              <div className="d-flex justify-content-between">
                <h1 className="custom-bg-text">{sport.name}</h1>
              </div>
            </div>

            <div class="card-body text-left text-color">
              <div class="text-left mt-3">
                <h3>Description :</h3>
              </div>
              <h4 class="card-text">{sport.description}</h4>
            </div>

            <div class="card-body text-left text-color">
              <div class="text-left mt-3">
                <h3>Coach :</h3>
              </div>
              <h4 class="card-text">{coach.firstName+" "}{coach.lastName}</h4>
            </div>


            

            <div class="card-footer custom-bg">
              <div className="d-flex justify-content-center">

              <p>
                  <span>
                    <h4>Price : &#8377;{sport.price}</h4>
                  </span>
                </p>

              </div>

              <div>

              <form class="row g-3" onSubmit={bookSport}>
  <div class="col-auto">
    <label for="date">Booking Date</label>
    <input type="date"  class="form-control" id="date" name="date" onChange={handleBookingInput} value={booking.checkIn} required/>
  </div>
  <div class="col-auto">
           <label for="date">Batch</label>
                <select
                  name="batch"
                  onChange={handleBookingInput}
                  className="form-control"
                >
                  <option value="">Select Batch</option>

                  {batch.map((b) => {
                    return (
                      <option value={b}> {b} </option>
                    );
                  })}
                </select>
              </div>
 

  <div className="d-flex justify-content-center">

  <div>
  <input type="submit" class="btn btn-sm custom-bg bg-color mb-3" value="Book Sport"/>
  </div>
   
  </div>  
 
</form>

              </div>

            </div>
          </div>
        </div>
      
      </div>

      <div className="row mt-4">
        <div className="col-sm-12">
          <h2>Other Sports:</h2>
          <div className="row row-cols-1 row-cols-md-4 g-4">
              
                  {sports.map((s) => {
                  return <SportsCard item={s} />;
                })} 
              </div>
        </div>
      </div>
    </div>
  );
};

export default Sports;
