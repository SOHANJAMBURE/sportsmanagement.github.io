import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AddSportsForm = () => {
  
  let navigate = useNavigate();

   const [coach, setCoach] = useState([]);
  const [selectedImage, setSelectedImage] = useState(null);

  const [sport, setSport] = useState({
    name: "",
    description: "",
    price: "",
    coachId:"",
  });

  const retrieveAllSportsUsers = async () => {
    const response = await axios.get("http://localhost:8585/api/user/sports");
    return response.data;
  };

  useEffect(() => {
    const getAllSportsUsers = async () => {
      const allSportsUsers = await retrieveAllSportsUsers();
      if (allSportsUsers) {
        setCoach(allSportsUsers.users);
      }
    };

    getAllSportsUsers();
  }, []);

  const handleInput = (e) => {
    setSport({ ...sport, [e.target.name]: e.target.value });
  };

  const saveGround = () => {
    const formData = new FormData();
    formData.append("image", selectedImage);
    formData.append("name", sport.name);
    formData.append("description", sport.description);
    formData.append("price", sport.price);
    formData.append("coachId", sport.coachId);

    axios
      .post("http://localhost:8585/api/sports/add", formData)
      .then((result) => {
        result.json().then((res) => {
          console.log(res);
  
          console.log(res.responseMessage);
             
          navigate("/home");
                
        });
        
      });
  };

  return (
    <div>
      <div className="mt-2 d-flex aligns-items-center justify-content-center">
        <div
          className="card form-card border-color custom-bg"
          style={{ width: "25rem" }}
        >
          <div className="card-header bg-color custom-bg-text text-center">
            <h5 className="card-title">Add Sports</h5>
          </div>
          <div className="card-body text-color">
            <form>
              <div className="mb-3">
                <label htmlFor="name" className="form-label">
                  <b>Sport Name</b>
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  onChange={handleInput}
                  value={sport.name}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="description" className="form-label">
                  <b>Sport Description</b>
                </label>
                <textarea
                  className="form-control"
                  id="description"
                  name="description"
                  rows="3"
                  onChange={handleInput}
                  value={sport.description}
                />
              </div>

              <div className="mb-3 mt-1">
                <label htmlFor="quantity" className="form-label">
                  <b>Price</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="price"
                  name="price"
                  onChange={handleInput}
                  value={sport.price}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">
                  <b>Sports Coach</b>
                </label>
              <select
                  name="coachId"
                  onChange={handleInput}
                  className="form-control"
                >
                  <option value="">Select Sports Coach</option>

                  {coach.map((c) => {
                    return (
                      <option value={c.id}> {c.firstName+" "+c.lastName} </option>
                    );
                  })}
                </select>
              </div>

              <div className="mb-3">
                <label htmlFor="image1" className="form-label">
                  <b> Select Sport Image</b>
                </label>
                <input
                  className="form-control"
                  type="file"
                  id="image"
                  name="image"
                  value={sport.image}
                  onChange={(e) => setSelectedImage(e.target.files[0])}
                />
              </div>

              <button
                type="submit"
                className="btn bg-color custom-bg-text"
                onClick={saveGround}
              >
                Add Sports
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddSportsForm;
