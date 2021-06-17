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
package org.deepinthink.magoko.boot.minio.condition;

import org.deepinthink.magoko.boot.minio.MinioConstants;
import org.deepinthink.magoko.boot.minio.config.MinioProperties.Credentials;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MinioCredentialsCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(
      ConditionContext context, AnnotatedTypeMetadata metadata) {
    ConditionMessage.Builder builder = ConditionMessage.forCondition("MinIO");
    try {
      BindResult<Credentials> required =
          Binder.get(context.getEnvironment())
              .bind(MinioConstants.PREFIX + ".credentials", Credentials.class);
      if (required.isBound()) {
        return ConditionOutcome.match();
      }

    } catch (BindException ignored) {
    }
    return ConditionOutcome.noMatch(builder.because("credentials not found"));
  }
}
