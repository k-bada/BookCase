package com.bookcase.bookcase.book.service;

import com.bookcase.bookcase.book.DTO.BookDto;
import com.bookcase.bookcase.book.DTO.BookDetailDto;
import com.bookcase.bookcase.book.DTO.BookDetailResponseDto;
import com.bookcase.bookcase.book.DTO.BookSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AladinBookService {
    @Value("${ALADIN_TTB_KEY}")
    private String ttbKey;

    //기본 Http 클라이언트 생성
    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BookSearchResponseDto searchBooks(String query){
        try {
            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("www.aladin.co.kr")
                            .path("/ttb/api/ItemSearch.aspx")
                            .queryParam("ttbkey", ttbKey)
                            .queryParam("Query", query)
                            .queryParam("QueryType", "Keyword")
                            .queryParam("MaxResults", 20)
                            .queryParam("start", 1)
                            .queryParam("SearchTarget", "Book")
                            .queryParam("output", "js")
                            .queryParam("Version", "20131101")
                            .build())
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.get("item");

            List<BookDto> result = new ArrayList<>();

            if (items != null) {

                for (JsonNode item : items) {

                    Integer page = null;

                    if(item.has("subInfo") && item.get("subInfo").has("itemPage")) {
                        page = item.get("subInfo").get("itemPage").asInt();
                    }

                    result.add(BookDto.builder()
                            .title(item.get("title").asText())
                            .author(item.get("author").asText())
                            .cover(item.get("cover").asText())
                            .isbn(item.get("isbn13").asText())
                            .publisher(item.get("publisher").asText())
                            .pubDate(item.get("pubDate").asText())
                            .itemPage(page)
                            .categoryName(item.get("categoryName").asText())
                            .build());
                }
            }
            return BookSearchResponseDto.builder()
                    .item(result)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("알라딘 도서 검색 실패", e);
        }
    }

    public BookDetailResponseDto getBookDetail(String isbn13) {

        try {
            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("www.aladin.co.kr")
                            .path("/ttb/api/ItemLookUp.aspx")
                            .queryParam("ttbkey", ttbKey)
                            .queryParam("itemIdType", "ISBN13")
                            .queryParam("ItemId", isbn13)
                            .queryParam("output", "js")
                            .queryParam("Version", "20131101")
                            .queryParam("OptResult", "SubInfo")
                            .build())
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(response);
            JsonNode item = root.get("item").get(0);

            Integer page = null;
            if (item.has("subInfo") && item.get("subInfo").has("itemPage")) {
                page = item.get("subInfo").get("itemPage").asInt();
            }

            BookDetailDto dto = BookDetailDto.builder()
                    .title(item.get("title").asText())
                    .author(item.get("author").asText())
                    .cover(item.get("cover").asText())
                    .isbn(item.get("isbn13").asText())
                    .publisher(item.get("publisher").asText())
                    .pubDate(item.get("pubDate").asText())
                    .itemPage(page)
                    .categoryName(item.get("categoryName").asText())
                    .description(item.get("description").asText())
                    .build();

            return BookDetailResponseDto.builder()
                    .item(dto)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("알라딘 도서 상세 조회 실패", e);
        }
    }

    //베스트셀러 목록 조회
    public BookSearchResponseDto getBestSellers(Integer categoryId) {
        try {
            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("www.aladin.co.kr")
                            .path("/ttb/api/ItemList.aspx")
                            .queryParam("ttbkey", ttbKey)
                            .queryParam("QueryType", "BestSeller")
                            .queryParam("MaxResults", 100)
                            .queryParam("start", 1)
                            .queryParam("SearchTarget", "Book")
                            .queryParam("CategoryId", categoryId == null ? 0 : categoryId)
                            .queryParam("output", "js")
                            .queryParam("Version","20131101")
                            .build())
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.get("item");

            List<BookDto> result = new ArrayList<>();

            if (items != null) {
                for (JsonNode item : items) {
                    result.add(BookDto.builder()
                            .title(item.get("title").asText())
                            .author(item.get("author").asText())
                            .cover(item.get("cover").asText())
                            .isbn(item.get("isbn13").asText())
                            .publisher(item.get("publisher").asText())
                            .pubDate(item.get("pubDate").asText())
                            .categoryName(item.get("categoryName").asText())
                            .build());
                }
            }
            return BookSearchResponseDto.builder()
                    .item(result)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("알라딘 베스트셀러 조회 실패", e);
        }
    }


}
