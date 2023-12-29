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

const NavPages = (props) => {
  const [height, setHeight] = React.useState(0);
  console.log("avatar", props.profile.avatar);
  return (
    <>
      <CssBaseline />
      <Top />
      <NavBav
        token={props.token}
        profile={props.profile}
        setProfile={props.setProfile}
        setToken={props.setToken}
        setHeight={setHeight}
        height={height}
      />
      <ScrollTop showBelow={150} />
      <Routes>
        <Route index element={<Home token={props.token} />} />
        <Route path="home" element={<Home token={props.token} />} />
        <Route
          path="library"
          element={
            props.token.trim() !== "" ? (
              <Library profile={props.profile} token={props.token} />
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
            props.token.trim() !== "" ? (
              <Profile
                profile={props.profile}
                height={height}
                token={props.token}
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
