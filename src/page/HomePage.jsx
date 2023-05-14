import Carousel from "./Carousel";
import axios from "axios";
import { useState, useEffect } from "react";
import SportsCard from "../SportsComponent/SportsCard";

const HomePage = () => {

  const [sports, setSports] = useState([]);

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

  

  return (
    <div className="container-fluid mb-2">
     <Carousel />
       
      <div className="mt-2 mb-5">
      <div className="row mt-4">
        <div className="col-sm-12">
          <div className="row row-cols-1 row-cols-md-4 g-4">
              
                  {sports.map((sport) => {
                  return <SportsCard item={sport} />;
                })} 
              </div>
        </div>
      </div>
      </div>
    </div>
  );
};

export default HomePage;
