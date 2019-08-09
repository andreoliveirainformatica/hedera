pragma solidity >=0.4.22 <0.6.0;

contract SellerCommission {
    // the contract's owner, set in the constructor
    address owner;

    string commission;

    constructor(string memory c) public {
        // set the owner of the contract for `kill()`
        owner = msg.sender;
        commission = c;
    }

    // return a string
    function comission() public view returns (string memory) {
        return commission;
    }

    // recover the funds of the contract
    function kill() public { if (msg.sender == owner) selfdestruct(msg.sender); }
}
