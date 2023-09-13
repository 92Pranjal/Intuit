import React, { useEffect, useState } from 'react';
import {Link} from 'react-router-dom'
const Home = () => {

    const[error,setError] = useState(null);
    const[isLoaded,setIsLoaded] = useState(false);
    const[users,setUsers] = useState([]);

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = () => {
        fetch("http://127.0.0.1:8080/api/workers")
        .then(res => res.json())
        .then(
            (data) => {
                setIsLoaded(true);
                setUsers(data);
            },
            (error) => {
                setIsLoaded(true);
                setError(error);
            }
        )
    }

    if(error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading....</div>;
    } else {
        return (
            <React.Fragment>
            <h3>Worker List</h3>
            <ul>
                { users.map(user => {
                    return (<li key={user.employeeNumber}>
                        <div>
                        <span>{user.firstName} {" "}</span>
                        
                        <span>{user.lastName}</span>
                        </div>
                        
                    </li>)
                })}
            </ul>
            <br/>
            <div>
                <Link to={"./Onboard"}>
                    Onboard Workers
                </Link>
                <br/>
                <Link to={"./Offboard"}>
                    Offboard Workers
                </Link>
                <br/>
                <Link to={"./UpdateContract"}>
                    Update Contract
                </Link>
            </div>
            </React.Fragment>
        );
    }
}
export default Home;