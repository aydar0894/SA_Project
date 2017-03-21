/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.jcommune.web.view;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;
import org.jtalks.jcommune.model.entity.JCUser;
import org.jtalks.jcommune.model.entity.Post;
import org.jtalks.jcommune.model.entity.Topic;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Andrei Alikov
 */
public class PostListRssViewerTest {
    private PostListRssViewer postListRssViewer;

    @BeforeMethod
    public void setUp() {
        postListRssViewer = new PostListRssViewer();
    }

    @Test
    public void buildFeedMetadataShouldUseDefaultValuesWhenThereIsNoBranchInModel() {
        Map<String, Object> model = new HashMap<>();
        Channel channel = new Channel();

        postListRssViewer.buildFeedMetadata(model, channel, getMockRequest());

        assertEquals(channel.getTitle(), PostListRssViewer.DEFAULT_FEED_TITLE);
        assertEquals(channel.getDescription(), PostListRssViewer.DEFAULT_FEED_DESCRIPTION);
        assertEquals(channel.getLink(), "http://localhost:8080/jcommune");
    }

    @Test
    public void buildFeedMetadataShouldUseBranchInfoWhenThereIsBranchInModel() {
        Map<String, Object> model = new HashMap<>();

        model.put("feedTitle", "my branch");
        model.put("feedDescription", "my description");
        model.put("urlSuffix", "/branches/42");

        Channel channel = new Channel();

        postListRssViewer.buildFeedMetadata(model, channel, getMockRequest());

        assertEquals(channel.getTitle(), "my branch");
        assertEquals(channel.getDescription(), "my description");
        assertEquals(channel.getLink(), "http://localhost:8080/jcommune/branches/42");
    }

    @Test
    public void buildFeedItemsShouldRedirectWhenThereIsNoPostList() throws IOException {
        Map<String, Object> model = new HashMap<>();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        List<Item> result = postListRssViewer.buildFeedItems(model, request, response);

        assertEquals(response.getStatus(), 302);
        assertNull(result);
    }

    @Test
    public void buildFeedItemsShouldReturnEmptyListIfPostsListIsEmpty() throws IOException {
        Map<String, Object> model = new HashMap<>();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        model.put("posts", new ArrayList<Post>());

        List<Item> result = postListRssViewer.buildFeedItems(model, request, response);

        assertTrue(result.isEmpty());
    }

    @Test
    public void buildFeedItemsShouldReturnItemsWithAuthorsFromTheModel() throws IOException {
        Map<String, Object> model = new HashMap<>();

        JCUser user = new JCUser("user1", "mymail@email.mydomain", "qwerty");
        JCUser user2 = new JCUser("user2", "mymail2@email.mydomain", "qwerty");
        Topic topic = new Topic(user, "my topic");

        Post post = new Post(user, "Texty text!");
        Post post2 = new Post(user2, "Reply to texty text");
        topic.addPost(post);
        topic.addPost(post2);

        model.put("posts", Arrays.asList(post, post2));

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        List<Item> result = postListRssViewer.buildFeedItems(model, request, response);

        assertEquals(result.get(0).getAuthor(), user.getUsername());
        assertEquals(result.get(1).getAuthor(), user2.getUsername());
    }

    @Test
    public void buildFeedItemsShouldReturnItemsWithPostContentFromTheModel() throws IOException {
        Map<String, Object> model = new HashMap<>();

        JCUser user = new JCUser("user1", "mymail@email.mydomain", "qwerty");
        JCUser user2 = new JCUser("user2", "mymail2@email.mydomain", "qwerty");
        Topic topic = new Topic(user, "my topic");

        Post post = new Post(user, "Texty text!");
        Post post2 = new Post(user2, "Reply to texty text");
        topic.addPost(post);
        topic.addPost(post2);

        model.put("posts", Arrays.asList(post, post2));

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        List<Item> result = postListRssViewer.buildFeedItems(model, request, response);

        assertEquals(result.get(0).getContent().getValue(), post.getPostContent());
        assertEquals(result.get(1).getContent().getValue(), post2.getPostContent());
    }

    @Test
    public void buildFeedItemsShouldReturnItemsWithTitleFromTheModel() throws IOException {
        Map<String, Object> model = new HashMap<>();

        JCUser user = new JCUser("user1", "mymail@email.mydomain", "qwerty");
        JCUser user2 = new JCUser("user2", "mymail2@email.mydomain", "qwerty");
        Topic topic = new Topic(user, "my topic");

        Post post = new Post(user, "Texty text!");
        Post post2 = new Post(user2, "Reply to texty text");
        topic.addPost(post);
        topic.addPost(post2);

        model.put("posts", Arrays.asList(post, post2));

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        List<Item> result = postListRssViewer.buildFeedItems(model, request, response);

        assertEquals(result.get(0).getTitle(), topic.getTitle());
        assertEquals(result.get(1).getTitle(), topic.getTitle());
    }

    @Test
    public void buildFeedItemsShouldReturnItemsWithLinksFromTheModel() throws IOException {
        Map<String, Object> model = new HashMap<>();

        JCUser user = new JCUser("user1", "mymail@email.mydomain", "qwerty");
        JCUser user2 = new JCUser("user2", "mymail2@email.mydomain", "qwerty");
        Topic topic = new Topic(user, "my topic");

        Post post = new Post(user, "Texty text!");
        post.setId(1);
        Post post2 = new Post(user2, "Reply to texty text");
        post2.setId(3);
        topic.addPost(post);
        topic.addPost(post2);

        model.put("posts", Arrays.asList(post, post2));

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<Item> result = postListRssViewer.buildFeedItems(model, getMockRequest(), response);

        assertEquals(result.get(0).getLink(), "http://localhost:8080/jcommune/posts/1");
        assertEquals(result.get(1).getLink(), "http://localhost:8080/jcommune/posts/3");
    }

    @Test
    public void buildFeedItemsShouldReturnItemsWithDatesFromTheModel() throws IOException {
        Map<String, Object> model = new HashMap<>();

        JCUser user = new JCUser("user1", "mymail@email.mydomain", "qwerty");
        JCUser user2 = new JCUser("user2", "mymail2@email.mydomain", "qwerty");
        Topic topic = new Topic(user, "my topic");

        Post post = new Post(user, "Texty text!");
        post.setId(1);
        Post post2 = new Post(user2, "Reply to texty text");
        post2.setId(3);
        topic.addPost(post);
        topic.addPost(post2);

        model.put("posts", Arrays.asList(post, post2));

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<Item> result = postListRssViewer.buildFeedItems(model, getMockRequest(), response);

        assertEquals(result.get(0).getPubDate(), post.getCreationDate().toDate());
        assertEquals(result.get(1).getPubDate(), post2.getCreationDate().toDate());
    }

    private HttpServletRequest getMockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setServerPort(8080);
        request.setScheme("http");
        request.setContextPath("/jcommune");

        return request;
    }
}
