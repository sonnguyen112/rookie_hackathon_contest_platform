import { useSelector } from "react-redux";

function BLASKItemHistory({ value,  onClick, quiz }) {

  const account = useSelector((state) => state.user.account);
  return (
    <div width="1" className="blask-item">
      <div className="abc">

      </div>
      <div className="blask-image">
        <img src={quiz.avatar} alt="" />
      </div>
      <div width="1" className="blask-info">
        <div width="1" className="blask-header">
          <div className="blask-title">{quiz.title || "One of your quiz"}</div>
        </div>

        <div className="blask-footer">
          <div className="blask-author">
            score: {value.score}
          </div>
          <div className="blask-edit-info">
            {account.role[0] === "ROLE_USER" ? <div>
              <button
                className="blask-start-btn"
                type="Start"
                quiz="Start"
                onClick={onClick}
              >
                Xem lại
              </button>
            </div> : null}

          </div>
        </div>
      </div>
    </div>
  );
}
export default BLASKItemHistory;
