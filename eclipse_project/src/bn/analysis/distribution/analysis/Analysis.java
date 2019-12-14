/* This file was generated by SableCC (http://www.sablecc.org/). */

package bn.analysis.distribution.analysis;

import bn.analysis.distribution.node.ADefDecimal;
import bn.analysis.distribution.node.ADefExponent;
import bn.analysis.distribution.node.ADefFloat;
import bn.analysis.distribution.node.ADefMantisse;
import bn.analysis.distribution.node.AIdEvidence;
import bn.analysis.distribution.node.AIdVariable;
import bn.analysis.distribution.node.AListStructures;
import bn.analysis.distribution.node.AMainResult;
import bn.analysis.distribution.node.APayloadStructure;
import bn.analysis.distribution.node.ASciFloat;
import bn.analysis.distribution.node.ASimpleStructures;
import bn.analysis.distribution.node.AStructureLegend;
import bn.analysis.distribution.node.EOF;
import bn.analysis.distribution.node.InvalidToken;
import bn.analysis.distribution.node.Node;
import bn.analysis.distribution.node.Start;
import bn.analysis.distribution.node.Switch;
import bn.analysis.distribution.node.TCl;
import bn.analysis.distribution.node.TDot;
import bn.analysis.distribution.node.TIdentifier;
import bn.analysis.distribution.node.TLBra;
import bn.analysis.distribution.node.TLPar;
import bn.analysis.distribution.node.TNcl;
import bn.analysis.distribution.node.TNoise;
import bn.analysis.distribution.node.TNumber;
import bn.analysis.distribution.node.TNumexp;
import bn.analysis.distribution.node.TP;
import bn.analysis.distribution.node.TPipe;
import bn.analysis.distribution.node.TProbabilityTok;
import bn.analysis.distribution.node.TQuote;
import bn.analysis.distribution.node.TRBra;
import bn.analysis.distribution.node.TRPar;
import bn.analysis.distribution.node.TSemi;
import bn.analysis.distribution.node.TSpace;
import bn.analysis.distribution.node.TSslash;
import bn.analysis.distribution.node.TTable;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAMainResult(AMainResult node);
    void caseASimpleStructures(ASimpleStructures node);
    void caseAListStructures(AListStructures node);
    void caseAPayloadStructure(APayloadStructure node);
    void caseADefFloat(ADefFloat node);
    void caseASciFloat(ASciFloat node);
    void caseADefMantisse(ADefMantisse node);
    void caseADefExponent(ADefExponent node);
    void caseADefDecimal(ADefDecimal node);
    void caseAStructureLegend(AStructureLegend node);
    void caseAIdEvidence(AIdEvidence node);
    void caseAIdVariable(AIdVariable node);

    void caseTDot(TDot node);
    void caseTTable(TTable node);
    void caseTProbabilityTok(TProbabilityTok node);
    void caseTSslash(TSslash node);
    void caseTIdentifier(TIdentifier node);
    void caseTNumber(TNumber node);
    void caseTNumexp(TNumexp node);
    void caseTPipe(TPipe node);
    void caseTQuote(TQuote node);
    void caseTLPar(TLPar node);
    void caseTRPar(TRPar node);
    void caseTLBra(TLBra node);
    void caseTRBra(TRBra node);
    void caseTP(TP node);
    void caseTSemi(TSemi node);
    void caseTSpace(TSpace node);
    void caseTNcl(TNcl node);
    void caseTCl(TCl node);
    void caseTNoise(TNoise node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}