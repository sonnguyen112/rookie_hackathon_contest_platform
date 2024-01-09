import _ from 'lodash'
import { useState, useEffect } from 'react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
const QuestionHistory = (props) => {
    const { data, index  } = props;

    const [isPreviewImage, setIsPreviewImage] = useState(false)

    const handleCheckbox = (event, aId, qId) => {

        props.handleCheckbox(aId, qId)
    }


    if (_.isEmpty(data)) {
        return (<>
        </>);
    }
    return (<>
        {data.image ?
            <div className='q-image'>
                <img
                    style={{ cursore: 'pointer' }}
                    onClick={() => setIsPreviewImage(true)}
                    src={`${data.image}`} />



                {isPreviewImage === true &&
                    <AddCircleIcon
                        image={`${data.image}`}
                        title={"Question Image"}
                        onClose={() => setIsPreviewImage(false)}
                    >
                    </AddCircleIcon>}
            </div>
            :
            <div className='q-image'></div>
        }
        <div className='question'>
            Question {index + 1} : {data.questionDescription}
        </div>
        <div className='answer'>
        
            {
            data.answers && data.answers.map((a, index) => {
               
                
                return (

                    <div key={`answer-${index}`} className="a-child">
                        <div className='form-check'>
                            <input className='form-check-input'
                                type="checkbox"
                                checked={a.isSelected}
                                // onChange={(event) => { handleCheckbox(event, a.id, data.questionId) }}
                                readOnly={true}
                            />
                            <label className='form-check-label'>
                                {a.answerText}
                            </label>
                            {
                                <>
                                    {a.isSelected && a.isCorrect && !a.correct &&  <CloseIcon className="incorrect" />}
                                    {a.correct && <CheckIcon className="correct" />}
                                </>
                            }
                        </div>

                    </div>
                )
            })}
        </div>
    </>);
}

export default QuestionHistory;