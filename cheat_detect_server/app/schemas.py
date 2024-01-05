from pydantic import BaseModel

class CheckCheatResponse(BaseModel):
    is_cheat: bool
    type_cheat: str

class CheckCheatRequest(BaseModel):
    image: str