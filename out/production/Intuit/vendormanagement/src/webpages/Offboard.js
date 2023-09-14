import React, {useState} from 'react';
import axios from 'axios';
const Offboard = () => {
    const data = {contractModifierId: "",organisationId: "",contractId: "",employeeId: ""};
    const [inputData , setInputData] = useState(data);
    const [responseData, setResponseData] = useState(null);
    const [error,setError] = useState(null);
    const handleData = (e) => {
        setInputData({...inputData,[e.target.name]: e.target.value})
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post("http://127.0.0.1:8080/api/mapping/offboard", {
            contractModifierId: inputData.contractModifierId,
            contractId: inputData.contractId,
            organisationId: inputData.organisationId,
            employeeId: inputData.employeeId
        })
        .then((response => {
            console.log('This is the response data', response);
            setResponseData(response.data);
        }))
        .catch(error => {
            console.log('There was an error!',error.response.data.message);
            setError(error.response.data.message)
        });
    }
    if(error){
        return <div>Error: {error}</div>
    } else {
        return(
            <React.Fragment>
                <div>
                    <h1>Worker Offboarding Page!</h1>
                </div>
                <div>
                    <label>Contract Modifier Id: </label>
                    <input type="text" name='contractModifierId' value={inputData.contractModifierId} onChange={handleData}></input> <br/>
                    <label>Organisation Id: </label>
                    <input type="text" name='organisationId' value={inputData.organisationId} onChange={handleData}></input> <br/>
                    <label>Contract Id: </label>
                    <input type="text" name='contractId' value={inputData.contractId} onChange={handleData}></input> <br/>
                    <label>Employee Id: </label>
                    <input type="text" name='employeeId' value={inputData.employeeId} onChange={handleData}></input> <br/>

                    <button onClick={handleSubmit}>Submit</button>
                </div>
                <div>
                    Offboarding verdict: 
                    {responseData}
                </div>
                </React.Fragment>
            );
        }
}
export default Offboard;