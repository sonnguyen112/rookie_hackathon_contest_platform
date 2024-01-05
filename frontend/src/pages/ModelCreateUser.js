import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import "./ManageUsers.scss";
import { toast } from "react-toastify";

import { useEffect } from "react";
import { postCreateNewUser } from "../services/apiService";
const ModelCreateUser = (props) => {
  const { show, setShow } = props;

  const handleClose = () => {
    setShow(false);
    setEmail("");
    setPassword("");
    setUsername("");

    setRole("2");
  };
  // const validateEmail = (email) => {
  //   return email.match(
  //     /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  //   );
  // };

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [role, setRole] = useState("2");

  const handleRoleChange = (event) => {
    setRole(event.target.value);
  };

  const handleSubmitCreateUser = async () => {
    console.log(role)
    try {
      const data = await postCreateNewUser(
        email,
        password,
        username,
        firstName,
        lastName,
        role
      );
      console.log(data);
      toast.success("success");
      handleClose();
      props.setCurrentPage(1);
      props.fetchListUsersWithPaginate(1);
    } catch (error) {
      toast.error("Network Error. Please try again later.");
    }
  };

  return (
    <>
      <Modal
        show={show}
        onHide={handleClose}
        backdrop="static"
        size="xl"
        className="modal-add-user"
      >
        <Modal.Header closeButton>
          <Modal.Title>Modal add news user</Modal.Title>
        </Modal.Header>
        <Modal.Body>
  <form className="row g-3">
    <div className="col-md-6 mb-3">
      <label className="form-label">Email</label>
      <input
        type="email"
        className="form-control"
        value={email}
        onChange={(event) => setEmail(event.target.value)}
      />
    </div>
    <div className="col-md-6 mb-3">
      <label className="form-label">Password</label>
      <input
        type="password"
        className="form-control"
        value={password}
        onChange={(event) => setPassword(event.target.value)}
      />
    </div>
    <div className="col-md-6 mb-3">
      <label className="form-label">First Name</label>
      <input
        type="text"
        className="form-control"
        value={firstName}
        onChange={(event) => setFirstName(event.target.value)}
      />
    </div>
    <div className="col-md-6 mb-3">
      <label className="form-label">Last Name</label>
      <input
        type="text"
        className="form-control"
        value={lastName}
        onChange={(event) => setLastName(event.target.value)}
      />
    </div>
    <div className="col-md-6 mb-3">
      <label className="form-label">Username</label>
      <input
        type="text"
        className="form-control"
        value={username}
        onChange={(event) => setUsername(event.target.value)}
      />
    </div>
    <div className="col-md-6 mb-3">
      <label className="form-label">Role</label>
      <select
        className="form-select"
        value={role}
        onChange={handleRoleChange}
      >
        <option value="">Select role</option>
        <option value="2">User</option>
        <option value="1">Admin</option>
      </select>
    </div>
  </form>
</Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={() => handleSubmitCreateUser()}>
            Save
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ModelCreateUser;
