
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
const ModalResult = (props) => {
  const { show, setShow, dataModalResult, setIsShowAnswer } = props;
  const handleClose = () => setShow(false);
  const handleShowResult = () => {
    setIsShowAnswer(true)
    setShow(false)
  };
  return (
    <>
      <Modal show={show} onHide={handleClose} backdrop="static" size="xl">
        <Modal.Header closeButton>
          <Modal.Title>Your Result</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div>Total Question : <b>{dataModalResult.totalQuestions}</b></div>
          <div>Total Correct : <b>{dataModalResult.correctQuestions}</b></div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleShowResult}>
            Show answers
          </Button>
          <Button variant="primary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ModalResult;
