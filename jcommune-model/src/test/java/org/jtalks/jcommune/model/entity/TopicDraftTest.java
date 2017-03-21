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
package org.jtalks.jcommune.model.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTimeUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.*;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * @author Dmitry S. Dolzhenko
 */
public class TopicDraftTest {

    private static Validator validator;

    @BeforeMethod
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validationOfFilledDraftShouldPass() {
        TopicDraft draft = ObjectsFactory.getDefaultTopicDraft();

        Set<ConstraintViolation<TopicDraft>> constraintViolations = validator.validate(draft);

        assertEquals(constraintViolations.size(), 0);
    }

    @Test
    public void validationOfEmptyDraftShouldFail() {
        TopicDraft draft = ObjectsFactory.getDefaultTopicDraft();

        draft.setTitle(null);
        draft.setContent(null);

        Set<ConstraintViolation<TopicDraft>> constraintViolations = validator.validate(draft);

        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void validationOfDraftWithoutTitleShouldPass() {
        TopicDraft draft = ObjectsFactory.getDefaultTopicDraft();

        draft.setTitle(null);

        Set<ConstraintViolation<TopicDraft>> constraintViolations = validator.validate(draft);

        assertEquals(constraintViolations.size(), 0);
    }

    @Test
    public void validationOfDraftWithTooLongTitleShouldFail() {
        TopicDraft draft = ObjectsFactory.getDefaultTopicDraft();

        draft.setTitle(RandomStringUtils.random(121));

        Set<ConstraintViolation<TopicDraft>> constraintViolations = validator.validate(draft);

        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void validationOfDraftWithoutContentShouldPass() {
        TopicDraft draft = ObjectsFactory.getDefaultTopicDraft();

        draft.setContent(null);

        Set<ConstraintViolation<TopicDraft>> constraintViolations = validator.validate(draft);

        assertEquals(constraintViolations.size(), 0);
    }

    @Test
    public void validationOfDraftWithTooLongContentShouldFail() {
        TopicDraft draft = ObjectsFactory.getDefaultTopicDraft();

        draft.setContent(RandomStringUtils.random(20001));

        Set<ConstraintViolation<TopicDraft>> constraintViolations = validator.validate(draft);

        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void updateLastSavedTimeShouldUpdateLastSavedFiled() throws Exception {
        TopicDraft draft = ObjectsFactory.getDefaultTopicDraft();

        DateTimeUtils.setCurrentMillisFixed(DateTimeUtils.currentTimeMillis() + 1000);

        draft.updateLastSavedTime();

        assertEquals(draft.getLastSaved().getMillis(), DateTimeUtils.currentTimeMillis());

        DateTimeUtils.setCurrentMillisSystem();
    }
}
