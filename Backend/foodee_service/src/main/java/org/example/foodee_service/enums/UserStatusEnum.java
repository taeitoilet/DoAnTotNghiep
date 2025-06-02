/*
 * BackendApplication, part of VCB Clone Project.
 * Copyright (C) 2024 Coang
 * mailto:contact AT yourdomain DOT com
 *
 * BackendApplication is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * BackendApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.example.foodee_service.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
  ACTIVE("ACTIVE"),
  DISABLED("INACTIVE"),
  DELETED("DELETED");

  private final String userStatus;

  UserStatusEnum(String userStatus) {
    this.userStatus = userStatus;
  }

  public static boolean isValid(String status) {
    for (UserStatusEnum s : UserStatusEnum.values()) {
      if (s.getUserStatus().equalsIgnoreCase(status)) {
        return true;
      }
    }
    return false;
  }
}
