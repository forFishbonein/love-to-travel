import { storage as localStorage } from "@/utils/storage";
export function getFlag() {
  return localStorage.get("flag");
}

export function setFlag(flag: boolean) {
  return localStorage.set("flag", flag);
}
