import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import "./ManageUsers.scss";
import { toast } from "react-toastify";

import { useEffect } from "react";
import { postCreateNewUser } from "../services/apiService";
import { useNavigate } from "react-router";
const ModelConfirmScreen = (props) => {
  const { show, setShow, startCapture, stopCapture, text, videoRef } = props;
  let navigate = useNavigate();
  const handleClose = () => {
    setShow(false);
  };

  const handleBack = () => {
    navigate("/library");
  };

  return (
    <>
      <Modal
        show={show}
   
        backdrop="static"
        size="l"
        className="modal-add-user"
      >
        <Modal.Header closeButton>
          <Modal.Title>Modal Confirm Share Screen</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <div className="d-flex justify-content-center align-items-center flex-column">
    <div>
      <Button variant="primary" onClick={startCapture}>
        Start Capture
      </Button>
    </div>
    <div className="mt-3">
      <Button variant="warning" onClick={handleBack}>
        Back List Quiz
      </Button>
    </div>
  </div>
        </Modal.Body>

        <Modal.Footer>
       
        
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ModelConfirmScreen;
