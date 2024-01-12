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
import ForgotPassword from "./ForgotPassword";
import ResetPassword from "./ResetPassword";
import { useParams } from 'react-router-dom';
import ManageUser from "./ManageUser";
import PrivateRoute from "../routes/PrivateRoute";
const NavPages = (props) => {
  const [height, setHeight] = React.useState(0);
  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
  const account = useSelector((state) => state.user.account);
  const ResetPasswordRoute = () => {
    const { token } = useParams();
  console.log(token)
    return <ResetPassword token={token} />;
  };
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
            account.access_token !== "" ? (
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
        <Route path="manage-users" element={
        
        <PrivateRoute>
<ManageUser />
        </PrivateRoute>
        } />

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
          path="forgot_password"
          element={
            <ForgotPassword
              setToken={props.setToken}
              setProfile={props.setProfile}
              height={height}
            />
          }
        />
          <Route path="reset_password/*" element={<ResetPassword />} />
        
        <Route
          path="profile"
          element={
            account.access_token !== "" ? (
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
