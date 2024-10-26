/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.network

internal object HttpRoutes {
    private const val REPOSITORY_BASE_URL = "https://api.github.com/repos/hellguy39/hellnotes"
    private const val RAW_BASE_URL = "https://raw.githubusercontent.com/hellguy39/hellnotes"

    const val RELEASES = "$REPOSITORY_BASE_URL/releases"

    const val PRIVACY_POLICY = "$RAW_BASE_URL/master/PRIVACY_POLICY.md"
    const val TERMS_AND_CONDITIONS = "$RAW_BASE_URL/master/TERMS_AND_CONDITIONS.md"
}
