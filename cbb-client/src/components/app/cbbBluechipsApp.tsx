import React, { useState, useEffect } from "react";
import {
  BrowserRouter as ReactRouter,
  Route as ReactRoute
} from "react-router-dom";

import Cookies from "universal-cookie";

import { Navbar } from "../navbar/navbar";
import { Market } from "../market/Market";
import { Portfolio } from "../portfolio/portfolio";
import { Trade } from "../trade/trade";
import { Calculator } from "../calculator/calculator";
import { Transactions } from "../transactions/transactions";
import { Rules } from "../gamerules/rules";
import { Leaderboard } from "../leaderboard/leaderboard";
import { LoignSignup } from "../landingpage/loignSignup";
import { Settings } from "../settings-page/settings";
import { Admin } from "../admin/admin";
import axios from "axios";

require("./cbbBluechips.scss");

export interface IAppProps {}

export const App: React.FC<IAppProps> = (props: IAppProps) => {
  const cookies = new Cookies();

  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(
    cookies.get("_live") != undefined
  ); //_live is cookie used at login to flag front end for nav

  const [adminPriv, setAdminPriv] = useState<boolean>(false);

  useEffect(() => {
    axios
      .get("/api/admin/role-check")
      .then(response => {
        setAdminPriv(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  return (
    <ReactRouter>
      <div id="app-wrapper">
        <Navbar isLoggedIn={isLoggedIn} adminPriv={adminPriv} />
        <ReactRoute exact path={"/login"}>
          <LoignSignup setIsLoggedIn={setIsLoggedIn} />
        </ReactRoute>
        <ReactRoute exact path={["/", "/portfolio"]}>
          <Portfolio />
        </ReactRoute>
        <ReactRoute exact path="/market">
          <Market />
        </ReactRoute>
        <ReactRoute exact path="/trade/:teamId">
          <Trade />
        </ReactRoute>
        <ReactRoute exact path="/calculator">
          <Calculator />
        </ReactRoute>
        <ReactRoute exact path="/settings">
          <Settings />
        </ReactRoute>
        <ReactRoute exact path="/admin">
          <Admin />
        </ReactRoute>
        <ReactRoute exact path="/transactions">
          <Transactions />
        </ReactRoute>
        <ReactRoute exact path={["/rules", "/how-to-play"]}>
          <Rules />
        </ReactRoute>
        <ReactRoute exact path="/leaderboard">
          <Leaderboard paypalDonationAmount={10} />
        </ReactRoute>
      </div>
    </ReactRouter>
  );
};
