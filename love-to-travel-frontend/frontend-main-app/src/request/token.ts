import { storage as localStorage } from "@/utils/storage";
export function getToken() {
  return localStorage.get("token");
}

export function setToken(token) {
  return localStorage.set("token", token);
}

export function removeToken() {
  return localStorage.remove("token");
}
