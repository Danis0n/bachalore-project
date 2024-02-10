import TypeData from "./Type";

export default interface ParticipantData {
  id: number,
  name: string,
  bic: string,
  type?: TypeData,
  registrationDate?: string,
}