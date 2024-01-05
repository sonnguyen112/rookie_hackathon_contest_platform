import * as React from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";


import Box from "@mui/material/Box";
import LinearProgress from "@mui/material/LinearProgress";
import LoginIcon from "@mui/icons-material/Login";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { useDispatch } from "react-redux";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { postForgotPassword, postLogin, postResetPassword } from "../services/apiService";
import { doLogin } from "../redux/action/userAction";
import { toast } from "react-toastify";

import Link from "@mui/material/Link";
function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="http://localhost:3000">
        BLASK
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const theme = createTheme();


export default function ResetPassword(props) {
  let navigate = useNavigate();
  const dispatch = useDispatch();
  const [remember, setRemember] = React.useState(false);
  const [isLoading, setLoading] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(false);
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');

console.log(token)
  
  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
  
    const password = data.get("password");
 
    const confirmpassword = data.get("confirmpassword");
    try {

      if (password !== confirmpassword) {
   
        toast.error("Passwords do not match.");
        setLoading(false);
        return;
      }
  
      setLoading(true);
  
      const signInData = {
        password: password,
      
      };
  
      let response = await postResetPassword(token,password);
      console.log(response)
      if (response.message == "200") {
        
        setLoading(false);
        navigate("/login");
   
      } 
    } catch (error) {
      toast.error("Some thing wrong")
      setLoading(false);
    
    }
  };
  


  return (
    <ThemeProvider theme={theme}>
      <Container
        component="main"
        className="wrapper"
        maxWidth="false"
        disableGutters
      >
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <div className="div">
          <span className="dot"></span>
        </div>
        <Container
          maxWidth="sm"
          sx={{ minHeight: `calc(100vh - ${props.height}px)` }}
        >
          <CssBaseline />
          <Box
            sx={{
              paddingTop: 3,
              padding: 5,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              backgroundColor: "#ffffff",
              borderRadius: 5,
            }}
          >
            <Avatar sx={{ m: 1, bgcolor: "primary.main" }}>
              <LoginIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
            Password Reset
            </Typography>
            <Box
              component="form"
              onSubmit={handleSubmit}
              noValidate
              sx={{ mt: 3 }}
            >
              <TextField
                margin="normal"
                required
                fullWidth
                id="password"
                label="password"
                name="password"
                autoComplete=""
                autoFocus
              />
                <TextField
                margin="normal"
                required
                fullWidth
                id="confirmpassword"
                label="Confirm password"
                name="confirmpassword"
                autoComplete=""
                autoFocus
              />
             
            
              <Button
                type="submit"
                fullWidth
                variant="contained"
                onClick={() => {
                  setLoading(true);
                }}
                sx={{ mt: 3, mb: 2 }}
              >
                Submit
              </Button>

              {errorMessage && (
                <Alert
                  severity="error"
                  sx={{ m: 2, width: { md: "400px", xl: "600px" } }}
                >
                  <AlertTitle>Error</AlertTitle>
                  <Typography variant="body2">
                  Password is not correct
                  </Typography>
                  <Typography variant="body2">
                    — <strong>Please type again!</strong>
                  </Typography>
                </Alert>
              )}
          
              {isLoading && <LinearProgress sx={{ mt: 2 }} />}
            </Box>
          </Box>
          <Copyright sx={{ mt: 5 }} />
        </Container>
      </Container>
    </ThemeProvider>
  );
}
