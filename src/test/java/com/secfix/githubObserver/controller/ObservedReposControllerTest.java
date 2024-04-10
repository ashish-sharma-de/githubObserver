//package com.secfix.githubObserver.controller;
//
//import com.secfix.githubObserver.model.ObservedRepo;
//import com.secfix.githubObserver.service.ObservedRepoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//public class ObservedReposControllerTest {
//
//    @Mock
//    private ObservedRepoService observedRepoService;
//
//    @InjectMocks
//    private ObservedReposController controller;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void shouldReturnOkWhenCreatingObservedRepo() {
//        ObservedRepo observedRepo = new ObservedRepo();
//        ResponseEntity<?> response = controller.createObservedRepo(observedRepo);
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//    @Test
//    public void shouldReturnNotFoundWhenGettingNonExistingObservedRepo() {
//        String id = "nonExistingId";
//        when(observedRepoService.getObservedRepo(id)).thenReturn(null);
//        ResponseEntity<?> response = controller.getObservedRepo(id);
//        assertEquals(404, response.getStatusCodeValue());
//    }
//
//    @Test
//    public void shouldReturnOkWhenDeletingExistingObservedRepo() {
//        String id = "existingId";
//        ObservedRepo observedRepo = new ObservedRepo();
//        when(observedRepoService.getObservedRepo(id)).thenReturn(observedRepo);
//        ResponseEntity<?> response = controller.deleteObservedRepo(id);
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//    @Test
//    public void shouldReturnNotFoundWhenUpdatingNonExistingObservedRepo() {
//        ObservedRepo observedRepo = new ObservedRepo();
//        observedRepo.setId("nonExistingId");
//        when(observedRepoService.getObservedRepo(observedRepo.getId())).thenReturn(null);
//        ResponseEntity<?> response = controller.updateObserved(observedRepo);
//        assertEquals(404, response.getStatusCodeValue());
//    }
//}