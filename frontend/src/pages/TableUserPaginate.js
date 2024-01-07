
import ReactPaginate from "react-paginate";


const TableUserPaginate = (props) => {
  const { listUsers, pageCount } = props;

  const handlePageClick = (event) => {
    console.log(event.selected + 1)

    props.fetchListUsersWithPaginate(+event.selected + 1);
    props.setCurrentPage(+event.selected + 1);

  };


  return (
    <>
     <table className="table table-hover table-bordered">
  <thead className="thead-dark">
    <tr>
      <th scope="col">#</th>
      <th scope="col">Username</th>
      <th scope="col">Email</th>
      <th scope="col">Role</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
    {listUsers && listUsers.length > 0 ? (
      listUsers.map((item, index) => (
        <tr key={`table-users-${index}`}>
          <th scope="row">{item.id}</th>
          <td>{item.username}</td>
          <td>{item.email}</td>
          <td>{item.role.name}</td>
          <td>
            
            <button
         style={{ marginRight: '20px' }}
              className="btn btn-success btn-sm mr-6"
              onClick={() => props.handleClickBtnDetail(item)}
              title=" View Detail"
            >
              <i className="fas fa-edit"></i>
              View Cheat Image
            </button>

            <button
         style={{ marginRight: '20px' }}
              className="btn btn-warning btn-sm mr-6"
              onClick={() => props.handleClickBtnUpdate(item)}
              title="Update"
            >
              <i className="fas fa-edit"></i>
              Update
            </button>
            {/* <button
              className="btn btn-danger btn-sm"
              onClick={() => props.handleClickBtnDelete(item)}
              title="Delete"
            >
              Delete
              <i className="fas fa-trash-alt"></i>
            </button> */}
          </td>
        </tr>
      ))
    ) : (
      <tr>
        <td colSpan={5} className="text-center">
          Not found data
        </td>
      </tr>
    )}
  </tbody>
</table>

    
      <div className="user-pagination">

      <ReactPaginate
        breakLabel="..."
        nextLabel="next >"
        onPageChange={handlePageClick}
        pageRangeDisplayed={5}
        marginPagesDisplayed={2}
        pageCount={pageCount}
        previousLabel="< Prev"
        pageClassName="page-item"
        pageLinkClassName="page-link"
        previousClassName="page-item"
        previousLinkClassName="page-link"
        nextClassName="page-item"
        nextLinkClassName="page-link"
        breakClassName="page-item"
        breakLinkClassName="page-link"
        containerClassName="pagination"
        activeClassName="active"
        renderOnZeroPageCount={null}
        forcePage={props.currentPage - 1}
      />
      </div>
    </>
  );
};

export default TableUserPaginate;
