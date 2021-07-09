package com.pcw.assignment.services;

import com.pcw.assignment.models.LendEntry;
import com.pcw.assignment.models.LendEntryKey;
import com.pcw.assignment.repositories.BooksRepository;
import com.pcw.assignment.repositories.LendEntriesRepository;
import com.pcw.assignment.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LendEntryService {

    private LendEntriesRepository lendEntriesRepository;
    private BooksRepository booksRepository;
    private UsersRepository usersRepository;
    public LendEntryService(LendEntriesRepository lendEntriesRepository,
                            BooksRepository booksRepository,
                            UsersRepository usersRepository) {
                            this.lendEntriesRepository = lendEntriesRepository;
                            this.booksRepository = booksRepository;
                            this.usersRepository = usersRepository;
    }

    public List<LendEntry> findAllLendEntries(){
        return lendEntriesRepository.findAll();
    }

    public LendEntry addLendEntry(LendEntryKey lendEntryKey) {
        LendEntry lendEntry = new LendEntry();
        lendEntry.setId(lendEntryKey);
        System.out.println(lendEntryKey.toString());
        System.out.println(booksRepository.getById(lendEntryKey.getBookId()).toString());
        System.out.println(usersRepository.getById(lendEntryKey.getUserId()).toString());
        lendEntry.setBook(booksRepository.getById(lendEntryKey.getBookId()));
        lendEntry.setUser(usersRepository.getById(lendEntryKey.getUserId()));
        lendEntry.setDateTime(LocalDateTime.now());
        return lendEntriesRepository.save(lendEntry);
    }

    public LendEntry getById(LendEntryKey id) {
        return lendEntriesRepository.getById(id);
    }
}
