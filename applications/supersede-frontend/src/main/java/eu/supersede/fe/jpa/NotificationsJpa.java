/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.model.Notification;
import eu.supersede.fe.model.User;

/**
 * Class that provides methods that query the database about notifications.
 */
public interface NotificationsJpa extends JpaRepository<Notification, Long>
{
    /**
     * Return the list of notifications for the given user, ordered by creation time.
     * @param user
     */
    List<Notification> findByUserOrderByCreationTimeDesc(User user);

    /**
     * Return the list of notifications read (or not read) by the given user, ordered by creation time.
     * @param user
     * @param read
     */
    List<Notification> findByUserAndReadOrderByCreationTimeDesc(User user, boolean read);

    /**
     * Return the number of notifications for the given user.
     * @param user
     */
    Long countByUser(User user);

    /**
     * Return the number of notifications read (or not read) by the given user.
     * @param user
     * @param read
     */
    Long countByUserAndRead(User user, boolean read);

    /**
     * Return the list of read (or not read) notifications for which an email has been sent (or not) and created before
     * the specified date.
     * @param read
     * @param mailSent
     * @param limit
     */
    List<Notification> findByReadAndEmailSentAndCreationTimeLessThan(boolean read, boolean mailSent, Date limit);
}