import React, { useEffect, useState } from "react";
import AddIcon from '@mui/icons-material/Add';
import ModelCreateUser from "./ModelCreateUser";
import ModelUpdateUser from "./ModelUpdateUser";
import ModelDeleteUser from "./ModelDeleteUser";
import TableUserPaginate from "./TableUserPaginate";
import { toast } from "react-toastify";
import { exportCSVUser, exportExcelUser, getUserWithPaginate } from "../services/apiService";
import ModelDetailUserCheated from "./ModelDetailUserCheated";
import { useSelector } from "react-redux";


const ManageUser = (props) => {
  const LIMIT_USER = 5;
  const [pageCount, setPageCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRole, setSelectedRole] = useState('');
  const [selectedUserCheated, setSelectedUserCheated] = useState('');
  const [showModelCreateUser, setShowModelCreateUser] = useState(false);
  const [showModalUpdateUser, setShowModalUpdateUser] = useState(false);
  const [showModalDetailUser, setShowModalDetailUser] = useState(false);
  const [dataUpdate, setDataUpdate] = useState({});
  const [showModelDeleteUser, setShowModelDeleteUser] = useState(false);
  const [dataDelete, setDataDelete] = useState({});
  const [listUsers, setListUsers] = useState([]);
  
  const handleSearchTermChange = (e) => {
    const value = e.target.value;
    setSearchTerm(value);
  };

  const handleSelectedRoleChange = (e) => {
    const value = e.target.value;

    if(value == 10){
      setSelectedUserCheated(value)
    }else{
      console.log(value)
      setSelectedRole(value);
      setSelectedUserCheated('');
    }
   
 
  };

  const handleClickBtnUpdate = (user) => {
    setShowModalUpdateUser(true);
    setDataUpdate(user);
  };
  const handleClickBtnDetail = (user) => {
    setShowModalDetailUser(true);
    setDataUpdate(user);
  };

  const handleClickBtnDelete = (user) => {
    setShowModelDeleteUser(true);
    setDataDelete(user);
  };

  const resetUpdateData = () => {
    setDataUpdate({});
  };
  
  const fetchListUsersWithPaginate = async (page, searchTerm, role,UserCheated) => {
  
    try {
      const res = await getUserWithPaginate(page, searchTerm, role,UserCheated);
    
      if (res) {
        setListUsers(res.listName);
        setPageCount(res.totalPages);
      } else {
        toast.error("Failed to fetch user list");
      }
    } catch (error) {
      console.error('Error fetching user list:', error);
    
    }
  };

  useEffect(() => {
    fetchListUsersWithPaginate(currentPage, '', '','');
   
  }, []);

  useEffect(() => {
    fetchListUsersWithPaginate(currentPage, searchTerm, selectedRole,selectedUserCheated);
  }, [currentPage, searchTerm, selectedRole,selectedUserCheated]);


  const handleExportCSV = async() => {
    const res = await exportCSVUser();
 
    
  };
  const handleExportExcel= async() => {
    window.location.href = 'http://localhost:8080/api/auth/export/excel';

    
  };
  return (
    <div className="manage-user-container">
      <div className="users-content">
        <div className="btn-and-search-container">
          <div className="btn-add-new">
            <button
              className="btn btn-primary btn-add-user"
              onClick={() => setShowModelCreateUser(true)}
              style={{ marginRight: '20px' }}
            >
              Add new users
            </button>
        
            <button
              className="btn btn-success btn-export-excel"
              onClick={() => handleExportExcel()}
              style={{ marginRight: '20px' }}
            >
              Export Excel
            </button>
          </div>
         
         
          <div className="search-and-filter-container">
            <input
              type="text"
              placeholder="Search users..."
              value={searchTerm}
              onChange={handleSearchTermChange}
            />
            <select
              value={selectedRole}
              onChange={handleSelectedRoleChange}
            >
              <option value="">All Roles</option>
              <option value="1">Admin</option>
              <option value="2">User</option>
              <option value="10">Users Cheated</option>
            </select>
          </div>
        </div>

        <div className="table-users-container">
          <TableUserPaginate
            listUsers={listUsers}
            handleClickBtnUpdate={handleClickBtnUpdate}
            handleClickBtnDelete={handleClickBtnDelete}
            handleClickBtnDetail={handleClickBtnDetail}
            fetchListUsersWithPaginate={fetchListUsersWithPaginate}
            pageCount={pageCount}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
          />
        </div>

        <ModelCreateUser
          show={showModelCreateUser}
          setShow={setShowModelCreateUser}
          fetchListUsersWithPaginate={fetchListUsersWithPaginate}
          currentPage={setCurrentPage}
          setCurrentPage={setCurrentPage}
        />
        <ModelUpdateUser
          show={showModalUpdateUser}
          setShow={setShowModalUpdateUser}
          dataUpdate={dataUpdate}
          fetchListUsersWithPaginate={fetchListUsersWithPaginate}
          currentPage={setCurrentPage}
          resetUpdateData={resetUpdateData}
        />
           <ModelDetailUserCheated
          show={showModalDetailUser}
          setShow={setShowModalDetailUser}
          dataUpdate={dataUpdate}
          fetchListUsersWithPaginate={fetchListUsersWithPaginate}
          currentPage={setCurrentPage}
          resetUpdateData={resetUpdateData}
        />
        <ModelDeleteUser
          show={showModelDeleteUser}
          setShow={setShowModelDeleteUser}
          dataDelete={dataDelete}
          fetchListUsersWithPaginate={fetchListUsersWithPaginate}
        />
      </div>
    </div>
  );
};

export default ManageUser;

