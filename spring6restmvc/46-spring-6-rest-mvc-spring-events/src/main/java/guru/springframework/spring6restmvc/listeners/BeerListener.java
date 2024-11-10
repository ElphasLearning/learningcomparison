package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.events.*;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.repositories.BeerAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class BeerListener {

    private final BeerMapper beerMapper;
    private final BeerAuditRepository beerAuditRepository;

    @Async
    @EventListener
    public void listen(BeerEvent event){


        val beerAudit = beerMapper.beerToBeerAudit(event.getBeer());

        String eventType=null;
        switch (event){
            case BeerCreatedEvent beerCreatedEvent->eventType="BEER_CREATED";
            case BeerUpdatedEvent beerUpdatedEvent ->eventType="BEER_UPDATED";
            case BeerDeletedEvent beerDeletedEvent ->eventType="BEER_DELETED";
            case BeerPatchedEvent beerPatchedEvent ->eventType="BEER_PATCHED";
            default -> eventType="UNKNOWN";
        }
        beerAudit.setAuditEventType(eventType);

        if (event.getAuthentication()!=null && event.getAuthentication().getName()!=null){
            beerAudit.setPrincipalName(event.getAuthentication().getName());
        }

        val savedBeerAudit = beerAuditRepository.save(beerAudit);
        log.info("Beer Audit saved : "+eventType+" "+savedBeerAudit.getId());

    }

}
