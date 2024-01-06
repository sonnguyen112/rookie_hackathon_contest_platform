import React, { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import "./ManageUsers.scss";
import { toast } from "react-toastify";

import _ from "lodash";
import { putUpdateUser } from "../services/apiService";
const ModalUpdateUser = (props) => {
  const { show, setShow, dataUpdate } = props;


  const validateEmail = (email) => {
    return email.match(
      /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
  };
  const handleRoleChange = (event) => {
    setRole(event.target.value);
  };
  const handleSubmitUpdateUser = async () => {
 
    const isValiEmail = validateEmail(email);
    if (!isValiEmail) {
      toast.error("Invalid email");
      return;
    }
   

    let data = await putUpdateUser(dataUpdate.id, email, username, firstName,lastName,password, role);

    if (data.message == "yes") {
      toast.success("SUCCESS");
      handleClose();
      await props.fetchListUsersWithPaginate(1);
    } else {
      toast.error("FAIL");

    }

  };

  const [email, setEmail] = useState("");

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [role, setRole] = useState(2);


  useEffect(() => {
    if (!_.isEmpty(dataUpdate)) {
      setEmail(dataUpdate.email);
      setFirstName(dataUpdate.firstName);
      setLastName(dataUpdate.lastName);
      setUsername(dataUpdate.username);
      setPassword("");
      setRole(dataUpdate.role.id);

    }
  }, [props.dataUpdate]);
  const handleClose = () => {
    setShow(false);
    setEmail("");
    setFirstName("");
    setLastName("");
    setUsername("");
    setPassword("");
    setRole("2");

    props.resetUpdateData();
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
    <div className="col-md-6">
      <label className="form-label">Email</label>
      <input
        type="email"
        className="form-control"
        value={email}
        onChange={(event) => setEmail(event.target.value)}
      />
    </div>

    <div className="col-md-6">
      <label className="form-label">First Name</label>
      <input
        type="text"
        className="form-control"
        value={firstName}
        onChange={(event) => setFirstName(event.target.value)}
      />
    </div>
    
    <div className="col-md-6">
      <label className="form-label">Last Name</label>
      <input
        type="text"
        className="form-control"
        value={lastName}
        onChange={(event) => setLastName(event.target.value)}
      />
    </div>

    <div className="col-md-6">
      <label className="form-label">Username</label>
      <input
        type="text"
        className="form-control"
        value={username}
        onChange={(event) => setUsername(event.target.value)}
      />
    </div>

    <div className="col-md-6">
      <label className="form-label">Password</label>
      <input
        type="text"
        className="form-control"
        value={password}
        onChange={(event) => setPassword(event.target.value)}
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
          <Button variant="primary" onClick={() => handleSubmitUpdateUser()}>
            Save
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ModalUpdateUser;
