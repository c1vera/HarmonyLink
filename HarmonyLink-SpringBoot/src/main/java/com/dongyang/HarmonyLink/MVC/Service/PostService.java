package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.PostRepository;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.PostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.TrackDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import com.dongyang.HarmonyLink.Manager.MapperManager.BuildDTOManager;
import com.dongyang.HarmonyLink.Manager.MapperManager.BuildEntityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostService {
    @PersistenceContext
    public EntityManager entityManager;

    private TrackService trackService; // 타 기능 관련해서 참조시에는 리포지토리 직접 참조 대신, 서비스 통해 참조하기.
    private PostRepository postRepository;

    public PostService(PostRepository postRepository, TrackService trackService) {
        this.postRepository = postRepository;
        this.trackService = trackService;
    }

    /** 게시글 조회수 증가 및 해당 게시글 '정보' 가져오기 */
    public ArticlePostDTO getArticle(Long postKey) {

        ArticleEntity entity = postRepository.findById(postKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않음"));

        entity.increaseView(); // 조회수 증가
        postRepository.save(entity); // 조회수가 증가된 entity 저장

        TrackDTO trackDTO = BuildDTOManager.toTrackDTO(entity.getTrack());
        log.info(trackDTO.getTrackName() + "을 보여줄겁니다");


        return ArticlePostDTO.toDTO(entity).setTrackInfo(trackDTO);
    }

    /** 특정 필터링의 게시글 '목록' 반환 */
    public Page<ArticlePostDTO> getFilteredList(String mbti, Pageable pageable) {
        List<ArticleEntity> articleList = postRepository.findByInputMbti(mbti, pageable);
        if(articleList.size() == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "에러 임시지정");

        List<ArticlePostDTO> articlePostList = new ArrayList<>();
        
        // Page<>  아니더라도, List<> 또한 Iterable 속성 지니므로 foreach 가능
        articleList.forEach(
                articleEntity -> {
                    PostDTO postDTO = BuildDTOManager.buildPostDTO(ArticlePostDTO.toDTO(articleEntity));

                    String trackKey = articleEntity.getTrack().getSpotifyKey();

                    TrackEntity trackEntity = trackService.getTrackBySpotifyKey(trackKey);
                    TrackDTO trackDTO = BuildDTOManager.toTrackDTO(trackEntity);

                    articlePostList.add(BuildDTOManager.buildArticlePostDTO(postDTO, trackDTO));
                }
        );

        return new PageImpl<>(articlePostList, pageable, articlePostList.size());
    }

    /** '모든' 게시글 반환(필터링 X) */
    public List<ArticlePostDTO> getAllArticle() {
        return postRepository.findAll().stream()
                .map(i -> ArticlePostDTO.toDTO(i)).toList();
    }

    /** 게시글 작성 */
    @Transactional
    public ArticlePostDTO postArticle(UserDTO user, PostDTO dto, TrackDTO trackDTO) {
        UserEntity writeUser = UserEntity.toEntity(user);

        /* 현재 사용자가 게시글에 작성하고자 하는 track이 본 서비스 DB에 존재하는지 확인하고, 없으면 삽입 */
        // postDTO의 field 값 일부를 추출하여 TrackDTO로 만들기
        // 만든 DTO 활용하여 트랙 유무 확인, 없으면 삽입하고 아니면 존재하는 값 가져오기
        Optional<TrackEntity> trackEntityOptional = trackService.inputTrack(trackDTO);

        if(trackEntityOptional.isEmpty()) {
            log.info("inputTrack() 부문 에러");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "트랙이 정상적으로 불러와지지 않음.");
        }

        ArticleEntity result = BuildEntityManager.buildArticleEntity(writeUser, dto, trackEntityOptional.get());

        postRepository.save(result);

        return ArticlePostDTO.toDTO(result);
    }

    
    /** 게시글 수정. 미완성 코드 */
    @Transactional
    public ArticlePostDTO patchArticle(UserDTO user, ArticlePostDTO dto, TrackDTO devideTrackDTO) {
        UserEntity loginUser = UserEntity.toEntity(user);
        // TrackEntity savedTrack = TrackEntity.toEntity(devideTrackDTO);

        ArticleEntity target = postRepository.findById(dto.getPost_key())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글 존재하지 않음"));

        Optional<TrackEntity> trackDTO = trackService.inputTrack(devideTrackDTO);

        if(target.getUser().getUserKey() != loginUser.getUserKey())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정자는 게시글을 작성한 인원이 아님");

        // 값 수정 -> track DTO 및 Entity 관련 기능 작성 완료 이후 추후 수정
        // target.patchEntity(ArticleEntity.toEntity(dto, loginUser, savedTrack)),savedTrack);

        ArticleEntity result = postRepository.save(target);

        return ArticlePostDTO.toDTO(result);
    }

    @Transactional
    public ArticlePostDTO deleteArticle(UserDTO user, PostDTO dto) {
        ArticleEntity target = postRepository.findById(dto.getPost_key())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글 존재하지 않음"));

        if(target.getUser().getUserKey() != user.getUserKey())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 희망자는 게시글을 작성한 인원이 아님.");

        postRepository.delete(target);

        return ArticlePostDTO.toDTO(target);
    }
}
