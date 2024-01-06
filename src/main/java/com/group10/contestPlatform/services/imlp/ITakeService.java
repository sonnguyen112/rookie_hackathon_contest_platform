package com.group10.contestPlatform.services.imlp;



import com.group10.contestPlatform.dtos.auth.TakeDetailsDTO;
import com.group10.contestPlatform.dtos.auth.UserTakeDetailsResponseDTO;
import com.group10.contestPlatform.entities.CheatInfo;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.repositories.TakeRepository;
import com.group10.contestPlatform.services.TakeService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ITakeService implements TakeService {
	@Autowired
	private TakeRepository takeRepository;

	@Override
	public UserTakeDetailsResponseDTO getTakeDetails(Long userId) {
		List<Take> userTakes = takeRepository.findByUserId(userId);

		List<TakeDetailsDTO> takeDetailsList = new ArrayList<>();

		for (Take take : userTakes) {
			TakeDetailsDTO takeDetailsDTO = new TakeDetailsDTO();
			takeDetailsDTO.setTakeId(take.getId());
			takeDetailsDTO.setQuizTitle(take.getQuiz().getTitle());

			List<String> imgUrlList = new ArrayList<>();
			for (CheatInfo cheatInfo : take.getCheatInfos()) {
				imgUrlList.add(cheatInfo.getImgUrl());
			}
			takeDetailsDTO.setCheatInfoImgUrls(imgUrlList);

			takeDetailsList.add(takeDetailsDTO);
		}

		UserTakeDetailsResponseDTO responseDTO = new UserTakeDetailsResponseDTO();
		responseDTO.setTakeDetails(takeDetailsList);

		return responseDTO;
	}
}
