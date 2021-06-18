/*
 * Copyright (c) 2021-present deepinthink. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deepinthink.magoko.boot.profile.condition;

import java.util.Objects;
import org.deepinthink.magoko.boot.profile.MatchAllProfile;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

public class MatchAllProfileCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    MultiValueMap<String, Object> attrs =
        metadata.getAllAnnotationAttributes(MatchAllProfile.class.getName());
    if (Objects.nonNull(attrs)) {
      for (Object value : attrs.get("value")) {
        String[] profiles = (String[]) value;
        Assert.notEmpty(profiles, "Must specify at least on profile");
        for (String profile : profiles) {
          if (!context.getEnvironment().acceptsProfiles(Profiles.of(profile))) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }
}