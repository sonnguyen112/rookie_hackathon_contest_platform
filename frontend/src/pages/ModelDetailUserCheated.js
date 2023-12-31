import React, { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import "./ModelDetailUserCheated.scss";
import { toast } from "react-toastify";
import MdEditor from "react-markdown-editor-lite";
import "react-markdown-editor-lite/lib/index.css";
import MarkdownIt from "markdown-it";
import _ from "lodash";
import { getUserDetailCheated, putUpdateUser, sendMailUser } from "../services/apiService";

import Card from "react-bootstrap/Card";
import ListGroup from "react-bootstrap/ListGroup";

const ModelDetailUserCheated = (props) => {
  const { show, setShow, dataUpdate } = props;
  const [idUser, SetIdUser] = useState("");
  const [takeDetails, setTakeDetails] = useState([]);
  const [contentMarkdown, setContentMarkdown] = useState("");
  const [contentHTML, setContentHTML] = useState("");
  const [imageArray, setImageArray] = useState([]);
  const mdParser = new MarkdownIt();
  useEffect(() => {
    const fetchData = async () => {
      try {
        if (!_.isEmpty(dataUpdate)) {
          console.log(dataUpdate.id);
          const response = await getUserDetailCheated(dataUpdate.id);
         
          SetIdUser(dataUpdate.id);

          setTakeDetails(response.takeDetails);

          const newImageArray = response.takeDetails.reduce((acc, item) => {
            return acc.concat(item.cheatInfoImgUrls);
          }, []);
          setImageArray(newImageArray);
         
        }
      } catch (error) {
        console.error("Error fetching user details:", error);

        toast.error("Error fetching user details");
      }
    };

    fetchData();
  }, [props.dataUpdate]);

  const handleClose = () => {
    setShow(false);
    SetIdUser("");
    props.resetUpdateData();
  };
  const handleSendQuizToEmail = async() => {
   
    let response = await sendMailUser(dataUpdate.email,contentHTML,imageArray);

    console.log(response)
    if (response.message == "200") {
      toast.success("Send email success");
    } else {
      toast.error("Send email fail");
    }
  };
  let handleEditorChange = ({ html, text }) => {
    setContentHTML(html);
    setContentMarkdown(text);
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
          <Modal.Title>User {idUser}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Card>
            <Card.Body>
              <Card.Title>User Details</Card.Title>
            </Card.Body>
          </Card>

          {/* Display user take details */}
          {takeDetails.map((takeDetail) => (
            <Card key={takeDetail.takeId} className="mt-3">
              <Card.Body>
                <Card.Title>{takeDetail.quizTitle} {takeDetail.takeId}</Card.Title>
                <div className="image-row">
                  {takeDetail.cheatInfoImgUrls.map((imgUrl, index) => (
                    <a href={imgUrl} target="_blank" rel="noopener noreferrer" key={index}>
                      <img
                        src={imgUrl}
                        alt={`cheat image ${index + 1}`}
                        className="img-thumbnail custom-image"
                        style={{ width: "300px", height: "auto" }}
                      />
                    </a>
                  ))}
                </div>
                <Button
                  variant="primary"
                  onClick={() => handleSendQuizToEmail(takeDetail)}
                  className="send-email-button"
                >
                  Send Quiz to Email
                </Button>
                <div className="form-group col-md-6">
                
                  <MdEditor
                   style={{ height: "200px", border: "1px solid #ced4da", borderRadius: "5px",marginTop: "10px",}}
                    renderHTML={(text) => mdParser.render(text)}
                    onChange={handleEditorChange}
                    value={contentMarkdown}
                  />
                </div>
              </Card.Body>
            </Card>
          ))}
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ModelDetailUserCheated;
