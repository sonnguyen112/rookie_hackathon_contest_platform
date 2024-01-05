import React, { useEffect, useState } from "react";
import AddIcon from '@mui/icons-material/Add';
import ModelCreateUser from "./ModelCreateUser";
import ModelUpdateUser from "./ModelUpdateUser";
import ModelDeleteUser from "./ModelDeleteUser";
import TableUserPaginate from "./TableUserPaginate";
import { toast } from "react-toastify";
import { getUserWithPaginate } from "../services/apiService";


const ManageUser = (props) => {
  const LIMIT_USER = 5;
  const [pageCount, setPageCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRole, setSelectedRole] = useState('');
  const [showModelCreateUser, setShowModelCreateUser] = useState(false);
  const [showModalUpdateUser, setShowModalUpdateUser] = useState(false);
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
    setSelectedRole(value);
  };

  const handleClickBtnUpdate = (user) => {
    setShowModalUpdateUser(true);
    setDataUpdate(user);
  };

  const handleClickBtnDelete = (user) => {
    setShowModelDeleteUser(true);
    setDataDelete(user);
  };

  const resetUpdateData = () => {
    setDataUpdate({});
  };

  const fetchListUsers = async (page, searchTerm, role) => {
  
    try {
      const res = await getUserWithPaginate(page, searchTerm, role);
      console.log("res",res)
      if (res) {
        setListUsers(res.listName);
        setPageCount(res.totalPages);
      } else {
        toast.error("Failed to fetch user list");
      }
    } catch (error) {
      console.error('Error fetching user list:', error);
      toast.error("Failed to fetch user list");
    }
  };

  useEffect(() => {
    fetchListUsers(currentPage, '', ''); // Initial fetch without search term or role
  }, []);

  useEffect(() => {
    fetchListUsers(currentPage, searchTerm, selectedRole);
  }, [currentPage, searchTerm, selectedRole]);



  return (
    <div className="manage-user-container">
      <div className="users-content">
        <div className="btn-and-search-container">
          <div className="btn-add-new">
            <button
              className="btn btn-primary"
              onClick={() => setShowModelCreateUser(true)}
            >
              <AddIcon />
              Add new users
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
            </select>
          </div>
        </div>

        <div className="table-users-container">
          <TableUserPaginate
            listUsers={listUsers}
            handleClickBtnUpdate={handleClickBtnUpdate}
            handleClickBtnDelete={handleClickBtnDelete}
            fetchListUsersWithPaginate={fetchListUsers}
            pageCount={pageCount}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
          />
        </div>

        <ModelCreateUser
          show={showModelCreateUser}
          setShow={setShowModelCreateUser}
          fetchListUsersWithPaginate={fetchListUsers}
          currentPage={setCurrentPage}
        />
        <ModelUpdateUser
          show={showModalUpdateUser}
          setShow={setShowModalUpdateUser}
          dataUpdate={dataUpdate}
          fetchListUsersWithPaginate={fetchListUsers}
          currentPage={setCurrentPage}
          resetUpdateData={resetUpdateData}
        />
        <ModelDeleteUser
          show={showModelDeleteUser}
          setShow={setShowModelDeleteUser}
          dataDelete={dataDelete}
          fetchListUsersWithPaginate={fetchListUsers}
        />
      </div>
    </div>
  );
};

export default ManageUser;

