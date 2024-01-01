import React from "react";

import { Routes, Route } from "react-router-dom";
import CssBaseline from "@mui/material/CssBaseline";
import NavBav from "../components/NavBav";
import ScrollTop from "../components/ScrollTop";
import Top from "../components/AutoScrollTop";

import Home from "./Home";
import Library from "./Library";
import Login from "./Login";
import Profile from "./Profile";
import Reports from "./Reports";
import Signup from "./Signup";
import NoPage from "./NoPage";
import { useSelector } from "react-redux";

const NavPages = (props) => {
  const [height, setHeight] = React.useState(0);
  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
  const account = useSelector((state) => state.user.account);
  console.log(account)
  return (
    <>
      <CssBaseline />
      <Top />
      <NavBav
        token={account.access_token}
        
        setHeight={setHeight}
        height={height}
      />
      <ScrollTop showBelow={150} />
      <Routes>
        <Route index element={<Home token={account.access_token} />} />
        <Route path="home" element={<Home token={account.access_token} />} />
        <Route
          path="library"
          element={
            account.access_token.trim() !== "" ? (
              <Library profile={props.profile} token={account.access_token} />
            ) : (
              <Login
                setToken={props.setToken}
                setProfile={props.setProfile}
                height={height}
              />
            )
          }
        />

        <Route path="signup" element={<Signup height={height} />} />
        <Route
          path="login"
          element={
            <Login
              setToken={props.setToken}
              setProfile={props.setProfile}
              height={height}
            />
          }
        />
        <Route
          path="profile"
          element={
            account.access_token.trim() !== "" ? (
              <Profile
                profile={props.profile}
                height={height}
                token={account.access_token}
                setToken={props.setToken}
                setProfile={props.setProfile}
              />
            ) : (
              <NoPage />
            )
          }
        />
        <Route path="*" element={<NoPage />} />
      </Routes>
    </>
  );
};

export default NavPages;
