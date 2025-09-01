package com.example.spring09.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring09.dto.BookDto;
import com.example.spring09.entity.Book;
import com.example.spring09.repository.BookRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor // 생성자 자동 생성
public class BookServiceImpl implements BookService {
	
	// Repository 객체 주입
	private final BookRepository bookRepo;
	

	@Transactional(readOnly=true)
	@Override
	public List<BookDto> getAll() {
		
		List<BookDto> dtoList = bookRepo.findAllNativeQuery()
				.stream().map(BookDto::toDto).toList();
		
		return dtoList;
	}

	@Transactional(readOnly=true)
	@Override
	public BookDto getBook(long id) {
		Book entity = bookRepo.findById(id).get();
		
		Book entity2= bookRepo.findById(id)
				.orElseThrow(()->new IllegalArgumentException("책이 존재하지 않습니다 id="+id));
		
		return BookDto.toDto(entity);
	}

	@Transactional
	@Override
	public void addBook(BookDto dto) {
		// dto 를 entity 로 변경해서 save() 메소드에 전달 (추가, 수정 겸용)
		bookRepo.save(Book.toEntity(dto));
		
	}
	
	@Transactional
	@Override
	public void updateBook(BookDto dto) {
		Book entity = bookRepo.findById(dto.getId())
				.orElseThrow(()->new IllegalArgumentException("수정할 회원이 존재하지 않아요 ; ; "));
		entity.setName(dto.getName());
		entity.setAuthor(dto.getAuthor());
		entity.setPublisher(dto.getPublisher());
	}

	@Transactional
	@Override
	public void deleteBook(long id) {
		
		if(!bookRepo.existsById(id)) {
			throw new IllegalArgumentException("삭제할 회원이 존재하지 않습니다. num="+id);
		}
		bookRepo.deleteById(id);
	}

}
