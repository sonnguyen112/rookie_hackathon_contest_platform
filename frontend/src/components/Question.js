import React from "react";
import "../style/play.css"
import AnswerPlaying from "./AnswerPlaying";
import Timer from "./Timer";

const Question = (props) => {
	return (
		<div>
			<div className="question">
				{props.data.question}
			</div>
			
            <Timer value={props.value} setTimeInt={props.setTimeInt} timeout={props.timeout}/> 
			<div className="button-overall">
				{props.data.options.map((item, index) => (
					<AnswerPlaying value={item.content} index={index} onClick={() => props.onClick(item.id, item.question)}></AnswerPlaying>
				))
				}
			</div>
		</div>
	);
};

export default Question;