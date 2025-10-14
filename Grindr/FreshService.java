package Jack2025.Grindr;

import java.util.HashMap;
import java.util.Map;

class FreshService {

    // Store the last upload timstamp for each user
    private final Map<Long, Long> lastUploadTime =  new HashMap<>();
    // Store when the "fresh" state expires
    private final Map<Long, Long> freshUntil = new HashMap<>();
    // Update user

    // Called to update user state
    public synchronized void freshState(PhotoEvent event)
    {
        long now = event.timeStamp;
        Long last = lastUploadTime.get(event.profileId);

        // 1. Ignore if uploaded within 24 hours
        if(last != null && now - last < 24 * 60 * 60 * 1000L)
        {
            System.out.println("Upload ignored for user " + event.profileId + ": within 24h of last upload.");
            return;
        }

        // Update the last upload time
        lastUploadTime.put(event.profileId, now);

        // Mark as fresh for 1 s
        freshUntil.put(event.profileId, now + 1 * 1000);
        System.out.println("User " + event.profileId + " is now fresh until " + freshUntil.get(event.profileId));
    }

    //
    public synchronized boolean isFresh(Long profileId)
    {
        Long expire = freshUntil.get(profileId);
        if(expire == null) return false;

        long now = System.currentTimeMillis();
        return now < expire;
    }

    public static void main(String[] args) throws InterruptedException {
        FreshService service = new FreshService();

        long now = System.currentTimeMillis();
        service.freshState(new PhotoEvent(1L, now)); // accepted
        System.out.println(service.isFresh(1L)); // true

        // Try again within 24h → ignored
        service.freshState(new PhotoEvent(1L, now + 1000));


        // After 1h, freshness expires
        Thread.sleep(1000); // simulate time passing
        System.out.println(service.isFresh(1L)); // likely false after 1h

        service.freshState(new PhotoEvent(1L, now + 24 * 60 * 60 * 1000L));
        System.out.println(service.isFresh(1L));
    }
}

class PhotoEvent{
    public Long profileId;
    public Long timeStamp;

    public PhotoEvent(Long profileId, Long timeStamp)
    {
        this.profileId = profileId;
        this.timeStamp = timeStamp;
    }
}

