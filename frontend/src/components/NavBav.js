import React, { useEffect, useRef } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import MenuIcon from "@mui/icons-material/Menu";
import Container from "@mui/material/Container";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Tooltip from "@mui/material/Tooltip";
import MenuItem from "@mui/material/MenuItem";
import Divider from "@mui/material/Divider";
import AdbIcon from "@mui/icons-material/Adb";
import LogoutIcon from "@mui/icons-material/Logout";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import AddIcon from "@mui/icons-material/Add";
import HomeIcon from "@mui/icons-material/Home";
import LibraryBooksIcon from "@mui/icons-material/LibraryBooks";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import AnalyticsIcon from "@mui/icons-material/Analytics";
import SportsEsportsIcon from "@mui/icons-material/SportsEsports";
import { red } from "@mui/material/colors";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "../style/navbav.css";
import { useDispatch, useSelector } from "react-redux";
import { doLogout } from "../redux/action/userAction";
import { toast } from "react-toastify";

import HistoryIcon from "@mui/icons-material/History";

const pages = ["Home", "Library", "Manages User", "History"];
const settings = ["Profile", "Sign out"];

function ResponsiveAppBar(props) {
  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
// const account = useSelector((state) => state.user.account);
  let navigate = useNavigate();
  const theme = createTheme({
    palette: {
      mode: "light",
    },
  });

  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);
  const account = useSelector((state) => state.user.account);


  const ref = useRef(null);
  const dispatch = useDispatch();
  useEffect(() => {
    props.setHeight(ref.current.clientHeight);
  }, [props]);

  const handleLogin = () => { };

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleClickOnUserMenu = (event) => {
    switch (settings.indexOf(event.target.innerText)) {
      case 0:
        break;
      case 1:
        let res = fetch("http://localhost:8080/api/auth/logout")
        if (res) {

          dispatch(doLogout())
          // navigate("/");
        } else {
          toast.error("False")
        }

        break;
      default:
        break;
    }
    handleCloseUserMenu();
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <ThemeProvider theme={theme}>
      <AppBar position="static" ref={ref}>
        <Container maxWidth="xl" color="primary light">
          <Toolbar id="back-to-top-anchor" disableGutters>
            <AdbIcon sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />

            <Typography
              component={Link}
              to="/"
              variant="h6"
              noWrap
              sx={{
                mr: 2,
                display: { xs: "none", md: "flex" },
                fontFamily: "monospace",
                fontWeight: 700,
                letterSpacing: ".3rem",
                color: "inherit",
                textDecoration: "none",
              }}
            >
              BLASK
            </Typography>
            <Divider
              orientation="vertical"
              variant="middle"
              sx={{ display: { xs: "none", md: "flex" } }}
              flexItem
            />

            <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
              <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleOpenNavMenu}
                color="inherit"
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorElNav}
                anchorOrigin={{
                  vertical: "bottom",
                  horizontal: "left",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "left",
                }}
                open={Boolean(anchorElNav)}
                onClose={handleCloseNavMenu}
                sx={{
                  display: { xs: "block", md: "none" },
                }}
              >
                <MenuItem
                  component={Link}
                  to="/"
                  key={pages[0]}
                  onClick={handleCloseNavMenu}
                >
                  <HomeIcon sx={{ mr: 1, fontSize: "medium" }} />
                  <Typography textAlign="center">{pages[0]}</Typography>
                </MenuItem>
                <MenuItem
                  component={Link}
                  to="library"
                  key={pages[1]}
                  onClick={handleCloseNavMenu}
                >
                  <LibraryBooksIcon sx={{ mr: 1, fontSize: "medium" }} />
                  <Typography textAlign="center">{pages[1]}</Typography>
                </MenuItem>
                <MenuItem
                  component={Link}
                  to="minigame"
                  key={pages[2]}
                  onClick={handleCloseNavMenu}
                >
                  <AnalyticsIcon sx={{ mr: 1, fontSize: "medium" }} />
                  <Typography textAlign="center">{pages[2]}</Typography>
                </MenuItem>
              </Menu>
            </Box>
            <Typography
              variant="h5"
              noWrap
              component={Link}
              to="/"
              sx={{
                mr: 2,
                display: { xs: "flex", md: "none" },
                flexGrow: 1,
                fontFamily: "monospace",
                fontWeight: 700,
                letterSpacing: ".3rem",
                color: "inherit",
                textDecoration: "none",
              }}
            >
              BLASK
            </Typography>
            <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
              <Button
                className="underline-button"
                component={Link}
                to="/home"
                key={pages[0]}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: "white", display: "flex" }}
              >
                <HomeIcon
                  sx={{
                    display: { xs: "none", md: "flex" },
                    mr: 1,
                    fontSize: "medium",
                  }}
                />
                {pages[0]}
              </Button>
              <Button
                className="underline-button"
                key={pages[1]}
                component={Link}
                to="/library"
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: "white", display: "flex" }}
              >
                <LibraryBooksIcon
                  sx={{
                    display: { xs: "none", md: "flex" },
                    mr: 1,
                    fontSize: "medium",
                  }}
                />
                {pages[1]}
              </Button>
              {/* <Button
                className="underline-button"
                component={Link}
                to="/minigame"
                key={pages[2]}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: "white", display: "flex" }}
              >
                <AnalyticsIcon
                  sx={{
                    display: { xs: "none", md: "flex" },
                    mr: 1,
                    fontSize: "medium",
                  }}
                />
                {pages[2]}
              </Button> */}
                 {isAuthenticated === true && account.role[0] === "ROLE_ADMIN" &&
                   <Button
                   className="underline-button"
                   component={Link}
                   to="/manage-users"
                   key={pages[2]}
                   onClick={handleCloseNavMenu}
                   sx={{ my: 2, color: "white", display: "flex" }}
                 >
                   <AnalyticsIcon
                     sx={{
                       display: { xs: "none", md: "flex" },
                       mr: 1,
                       fontSize: "medium",
                     }}
                   />
                   {pages[2]}
                 </Button>
                 }

{account.role[0] === "ROLE_USER" && isAuthenticated === true
                    &&
                      <Button
                    className="underline-button"
                    component={Link}
                    to="/history"
                    key={pages[3]}
                    onClick={handleCloseNavMenu}
                    sx={{ my: 2, color: "white", display: "flex" }}
                  >
                    <HistoryIcon
                      sx={{
                        display: { xs: "none", md: "flex" },
                        mr: 1,
                        fontSize: "medium",
                      }}
                    />
                    {pages[3]}
                  </Button>
                    }
             
              
            </Box>

            {props?.token !== "" ? (
              <>
                {account.role[0] === "ROLE_ADMIN" ?
                  <Tooltip title="Create" style={{ marginRight: "10px" }}>
                    <IconButton
                      component={Link}
                      to="/create-quiz"
                      variant="contained"
                      sx={{
                        backgroundColor: "#fff",
                        color: "#1976d2",
                        "&:hover": { color: "#fff" },
                      }}
                    >
                      <AddIcon sx={{ fontSize: "25px" }} />
                    </IconButton>
                  </Tooltip> : null}

                {/* <Tooltip title="Play">
                  <IconButton
                    component={Link}
                    to="joinin"
                    variant="contained"
                    sx={{
                      m: 1,
                      backgroundColor: "#fff",
                      color: "#1976d2",
                      "&:hover": { color: "#fff" },
                    }}
                  >
                    <PlayArrowIcon sx={{ fontSize: "25px" }} />
                  </IconButton>
                </Tooltip> */}

                <Box sx={{ flexGrow: 0 }}>
                  <Tooltip title="Settings">
                    <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                      <Avatar
                      // alt={account.firstname}
                      // src={account.avatar}
                      />
                    </IconButton>
                  </Tooltip>
                  <Menu
                    sx={{ mt: "45px" }}
                    id="menu-appbar"
                    anchorEl={anchorElUser}
                    anchorOrigin={{
                      vertical: "top",
                      horizontal: "right",
                    }}
                    keepMounted
                    transformOrigin={{
                      vertical: "top",
                      horizontal: "right",
                    }}
                    open={Boolean(anchorElUser)}
                    onClose={handleCloseUserMenu}
                  >
                    {/* <MenuItem
                      component={Link}
                      to="profile"
                      key={settings[0]}
                      onClick={handleClickOnUserMenu}
                    >
                      <AccountCircleIcon color="success" sx={{ mr: 1 }} />
                      <Typography textAlign="center">{settings[0]}</Typography>
                    </MenuItem> */}
                    <Divider />
                    <MenuItem key={settings[1]} onClick={handleClickOnUserMenu}>
                      <LogoutIcon sx={{ mr: 1, color: red[500] }} />
                      <Typography textAlign="center">{settings[1]}</Typography>
                    </MenuItem>
                  </Menu>
                </Box>
              </>
            ) : (
              <Box sx={{ flexGrow: 0 }}>
                {/* <Button
                  component={Link}
                  to="joinin"
                  variant="contained"
                  sx={{ m: 1, backgroundColor: "#e3f2fd", color: "#000" }}
                >
                  Play
                </Button> */}
                <Button
                  component={Link}
                  to="login"
                  variant="text"
                  color="inherit"
                  onClick={handleLogin}
                >
                  Log In
                </Button>
              </Box>
            )}
          </Toolbar>
        </Container>
      </AppBar>
    </ThemeProvider>
  );
}
export default ResponsiveAppBar;
