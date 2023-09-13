import React, {useState} from 'react';
import axios from 'axios';
const UpdateContract = () => {
    const data = {contractModfier: "",organisationId: "",contractId: "",employeeId: "", existingContract: ""};
    const [inputData , setInputData] = useState(data);
    const [responseData, setResponseData] = useState({});
    const [error,setError] = useState(null);
    const handleData = (e) => {
        setInputData({...inputData,[e.target.name]: e.target.value})
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.put(`http://127.0.0.1:8080/api/mapping/${inputData.existingContract}/revise`, {
            contractModfier: inputData.contractModfier,
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
                    <h1>Welcome to Contract Reassignment Page!</h1>
                </div>
                <div>
                    <label>Contract Modifier Id: </label>
                    <input type="text" name='contractModfier' value={inputData.contractModfier} onChange={handleData}></input> <br/>
                    <label>Existing contract: </label>
                    <input type="text" name='existingContract' value={inputData.existingContract} onChange={handleData}></input> <br/>
                    <label>Organisation Id: </label>
                    <input type="text" name='organisationId' value={inputData.organisationId} onChange={handleData}></input> <br/>
                    <label>Contract to be updated to: </label>
                    <input type="text" name='contractId' value={inputData.contractId} onChange={handleData}></input> <br/>
                    <label>Employee Id: </label>
                    <input type="text" name='employeeId' value={inputData.employeeId} onChange={handleData}></input> <br/>

                    <button onClick={handleSubmit}>Submit</button>
                </div>
                <div>
                    {
                        <ul>
                            <li>First Name: {responseData.workerFirstName}</li>
                            <li>Last Name: {responseData.workerLastName}</li>
                            <li>Role: {responseData.role}</li>
                            <li>Email: {responseData.workerEmail}</li>
                            <li>Start Date: {responseData.startDate}</li>
                            <li>Status: {responseData.serviceStatus}</li>
                        </ul>
                    }
                </div>
                </React.Fragment>
            );
        }
}
export default UpdateContract;