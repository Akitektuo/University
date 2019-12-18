def validate_address(address, existent_addresses):
    if len(address) < 1:
        raise Exception("The address name cannot be empty")
    
    for existent_address in existent_addresses:
        if address == existent_address.name:
            return
    raise Exception("No address found with the name " + address)