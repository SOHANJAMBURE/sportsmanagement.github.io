import { Link } from "react-router-dom";

const SportsCard = (sports) => {
  return (
    <div className="col">
    <div class="card border-color rounded-card card-hover product-card custom-bg h-100">
       <img
        src={"http://localhost:8585/api/sports/" + sports.item.image}
        class="card-img-top rounded mx-auto d-block m-2"
        alt="img"
        style={{
          maxHeight: "270px",
          maxWidth: "100%",
          width: "auto",
        }}
      /> 

      <div class="card-body text-color">
        <h5 class="card-title d-flex justify-content-between">
          <div>
            <b>{sports.item.name}</b>
          </div>
        </h5>
        <p className="card-text">
          <b>{sports.item.description}</b>
        </p>

      </div>
      <div class="card-footer">
        <div className="text-center text-color">
          <p>
            <span>
              <h4>Price : &#8377;{sports.item.price}</h4>
            </span>
          </p>
        </div>
        <div className="d-flex justify-content-center">
          <Link
            to={`/book/sports/${sports.item.id}/coach/${sports.item.coachId}`}
            className="btn bg-color custom-bg-text"
          >
            Book Now
          </Link>
        </div>
      </div>
    </div>
    </div>
  );
};

export default SportsCard;
