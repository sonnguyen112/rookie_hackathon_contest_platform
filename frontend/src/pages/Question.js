import _ from 'lodash'
import { useState } from 'react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
const Question = (props) => {
    const { data, index, isShowAnswer } = props;

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
                    style={{
                        cursor: 'pointer',
                        border: '1px solid #ccc',
                        borderRadius: '5px',
                        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                        maxWidth: '100%',
                        height: 'auto',
                    }}
                    onClick={() => setIsPreviewImage(true)}
                    src={`${data.image}`}
                />
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
            {data.answers && data.answers.map((a, index) => {

                return (

                    <div key={`answer-${index}`} className="a-child">
                        <div className='form-check'>
                            <input className='form-check-input'
                                type="checkbox"
                                checked={a.isSelected}
                                onChange={(event) => { handleCheckbox(event, a.id, data.questionId) }}
                            />
                            <label className='form-check-label'>
                                {a.answerText}
                            </label>
                            {isShowAnswer === true &&
                                <>
                                    {a.isSelected === true && a.isCorrect === false && <CloseIcon className="incorrect" />}
                                    {a.isCorrect === true && <CheckIcon className="correct" />}
                                </>
                            }
                        </div>

                    </div>
                )
            })}
        </div>
    </>);
}

export default Question;