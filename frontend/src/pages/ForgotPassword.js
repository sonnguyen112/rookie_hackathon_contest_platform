import * as React from "react";
import { useNavigate } from "react-router-dom";
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
import { postForgotPassword, postLogin } from "../services/apiService";
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


export default function ForgotPassword(props) {
  let navigate = useNavigate();
  const dispatch = useDispatch();
  const [remember, setRemember] = React.useState(false);
  const [isLoading, setLoading] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(false);


  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
  
    const email = data.get("email");
 
  
    try {
      setLoading(true);
  
      const signInData = {
        email: email,
      
      };
  
      let response = await postForgotPassword(email);
      console.log(response.message)
      if (response.message == "200") {
        
        setLoading(false);
        toast.success("Please check your email")
   
      } else if (response.message == "nouser") {
        setLoading(false);
    
  
        toast.error("Email is not correct")
      }
    } catch (error) {
      console.error("Error during login:", error);
      setLoading(false);
    
    }
  };
  

  const handleRemember = (event) => {
    setRemember(event.target.checked);
    console.log(event.target.checked);
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
                id="email"
                label="email"
                name="email"
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
                    Email is not correct
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
