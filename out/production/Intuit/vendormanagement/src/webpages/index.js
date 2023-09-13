import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Routes,
  BrowserRouter
} from "react-router-dom";
import Home from './Home';
import Onboard from './Onboard';
import Offboard from './Offboard';
import UpdateContract from './UpdateContract';
const Webpages = () => {
    return(
        <BrowserRouter>
        <Routes>
            <Route exact path="/" element= {<Home/>} />
            <Route path = "/onboard" element = {<Onboard/>} />
            <Route path = "/offboard" element = {<Offboard/>} />
            <Route path = "/updateContract" element = {<UpdateContract/>} />
        </Routes>
        </BrowserRouter>
    );
};
export default Webpages;