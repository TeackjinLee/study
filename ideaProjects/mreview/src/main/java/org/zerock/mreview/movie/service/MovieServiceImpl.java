package org.zerock.mreview.movie.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mreview.Repository.MovieImageRepository;
import org.zerock.mreview.Repository.MovieRepository;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.movie.dto.MovieDTO;
import org.zerock.mreview.movie.dto.MovieImageDTO;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("moveie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);
        imageRepository.saveAll(movieImageList);
//        movieImageList.forEach(movieImage -> {
//            imageRepository.save(movieImage);
//        });

        return movie.getMno();
    }
}
