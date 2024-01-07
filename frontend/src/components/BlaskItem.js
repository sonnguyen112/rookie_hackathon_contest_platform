import { useSelector } from "react-redux";
function BLASKItem({ username, value, avatar, onClick, deleteQuiz, editQuiz }) {
  const account = useSelector((state) => state.user.account);
  return (
    <div width="1" className="blask-item">
      <div className="abc">
        {/* <div>
          <div className="blask-checkbox">
            <input id="cb" type="checkbox" aria-label="Select blask"></input>
          </div>
        </div> */}
      </div>
      <div className="blask-image">
        <img src={value.avatar} alt="" />
      </div>
      <div width="1" className="blask-info">
        <div width="1" className="blask-header">
          <div className="blask-title">{value.title || "One of your quiz"}</div>
          {account.role[0] === "ROLE_ADMIN" ? <div className="blask-utils-btn">
            <div>
              <button className="btn" onClick={deleteQuiz}>
                <i className="fa-solid fa-pencil">Delete</i>
              </button>
            </div>
            <div>
              <button className="btn" onClick={editQuiz}>
                <i className="fa-solid fa-ellipsis-vertical">Edit</i>
              </button>
            </div>
          </div> : null}

        </div>

        <div className="blask-footer">
          <div className="blask-author">
            {/* <img src={avatar} alt="" className="blask-avatar" />
            {username || "You"} */}
          </div>
          <div className="blask-edit-info">
            {account.role[0] === "ROLE_USER" ? <div>
              <button
                className="blask-start-btn"
                type="Start"
                value="Start"
                onClick={onClick}
              >
                Start
              </button>
            </div> : null}

            {/* <div>
              <button className="blask-assign-btn" type="Assign" value="Assign">
                Assign
              </button>
            </div> */}

            <span>{value.edit_time || ""}</span>
          </div>
        </div>
      </div>
    </div>
  );
}
export default BLASKItem;
