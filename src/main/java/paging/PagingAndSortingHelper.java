package paging;

import com.group10.contestPlatform.dtos.auth.UserResponse;
import com.group10.contestPlatform.repositories.UserRepository;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
@Getter
public class PagingAndSortingHelper {
    private String listName;
    private String sortField;
    private String sortDir;
    private String keyword;

    public PagingAndSortingHelper(String listName,
                                  String sortField, String sortDir, String keyword) {

        this.listName = listName;
        this.sortField = sortField;
        this.sortDir = sortDir;
        this.keyword = keyword;
    }

    public void updateModelAttributes(int pageNum, Page<?> page, UserResponse result) {
        List<?> listItems = page.getContent();
        int pageSize = page.getSize();

        long startCount = (pageNum - 1) * pageSize + 1;
        long endCount = Math.min(startCount + pageSize - 1, page.getTotalElements());
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        result.setCurrentPage(pageNum);
        result.setTotalPages(page.getTotalPages());
        result.setStartCount(startCount);
        result.setEndCount(endCount);
        result.setTotalItems(page.getTotalElements());
        result.setListName(listItems);
    }

    public <T, ID> void listEntities(int pageNum, int pageSize, UserRepository repo, UserResponse result) {
        Pageable pageable = createPageable(pageSize, pageNum);
        Page<T> page = null;
        if (keyword != null) {
          page = (Page<T>) repo.findAll(keyword, pageable);
        }else{
            page = (Page<T>) repo.findAll(pageable);
        }


        updateModelAttributes(pageNum, page, result);
    }

    public Pageable createPageable(int pageSize, int pageNum) {



        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();



        return PageRequest.of(pageNum - 1, pageSize, sort);
    }



}
